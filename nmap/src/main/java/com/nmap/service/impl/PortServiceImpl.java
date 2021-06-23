package com.nmap.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nmap.data.PortRepo;
import com.nmap.data.PortThresholdRepo;
import com.nmap.model.Port;
import com.nmap.model.PortFetchThreshold;
import com.nmap.model.PortInformation;
import com.nmap.service.PortService;
import com.nmap.util.DateUtil;
import com.nmap.util.NmapperConstant;
import com.nmap.util.PortInfoOrganizer;
import com.nmap.util.SortPortsByDateComparator;
import com.nmap.util.SortPortsByHourComparator;
import com.nmap.util.SortPortsByMinuteComparator;
import com.nmap.util.SortPortsByMonthComparator;
import com.nmap.util.SortPortsBySecondComparator;
import com.nmap.util.SortPortsByYearComparator;

@Service
public class PortServiceImpl implements PortService {

	@Autowired
	private PortRepo portRepository;
	
	@Autowired
	private PortThresholdRepo portThresholdRepo;
	
	@Autowired
	private DateUtil dateUtil;
	
	@Autowired
	private PortInfoOrganizer portInfoOrganizer;
	
	@Value("${port.scan.history.threshold}")
	private String threshold;

	public PortInformation getOpenPortsByHostName(String hostName) {
		return getAllThePortInformation(hostName);
	}

	private PortInformation getAllThePortInformation(String hostName) {
		System.out.println(threshold);
		
		// Regex usage to figure out open ports from terminal output
		Pattern pattern = Pattern.compile(NmapperConstant.PATTERN_FOR_PORT);
		Process process = null;
		List<Port> openPorts = new ArrayList<Port>();
		StringBuffer stringBuffer = new StringBuffer();
		
		List<PortFetchThreshold> portFetchThresholds = portThresholdRepo.findByHostname(hostName);
		Integer latestIndex = 0;
		if(portFetchThresholds.size()>0 && portFetchThresholds.get(0)!=null) {
			latestIndex = portFetchThresholds.get(0).getLatest()+1;
			PortFetchThreshold out = portFetchThresholds.get(0);
			out.setLatest(latestIndex);
			portThresholdRepo.save(out);
		}else {
			PortFetchThreshold portFetchThreshold = new PortFetchThreshold(hostName, latestIndex);
			portThresholdRepo.save(portFetchThreshold);
		}
		
		//Fetch date here to give one date time per request
		String createdOn = dateUtil.getCurrentDateTimeInUTCStringFormat();
		try {
			process = Runtime.getRuntime().exec(NmapperConstant.GET_OPEN_PORT_COMMAND + " " + hostName);
			System.out.println("Please wait ...");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), NmapperConstant.UTF8.toString()));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line + "\n");
				if (pattern.matcher(line).find()) {
					String[] tempOutput = stringBuffer.toString().split(" ");
					tempOutput = clean(tempOutput);
					// TODO: check if the size is three;
					Port port = new Port(tempOutput[0].trim().split("/")[0],tempOutput[0].trim().split("/")[1], tempOutput[1].trim(), tempOutput[2].trim()
							,hostName.toLowerCase(),createdOn,latestIndex);
					portRepository.save(port);
					openPorts.add(port);
				}
				stringBuffer = new StringBuffer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//We will write logic here to add extra information.
		
		latestIndex = (latestIndex- Integer.valueOf(threshold))+1;
		latestIndex = (latestIndex < 0) ? 0 : latestIndex; 
		
		List<Port> listOfPorts = portRepository.findByHostnameAndLatestGreaterThanEqual(hostName,latestIndex);
		
		System.out.println("Before comparing");
		listOfPorts.stream().forEach(x->{
			System.out.println(x.getId()+"--"+x.getPortInfoCreateOn());
		});
		
		listOfPorts = listOfPorts.stream().sorted(getDateComparator()).collect(Collectors.toList());
		
		System.out.println("After comparing");
		listOfPorts.stream().forEach(x->{
			System.out.println(x.getId()+"--"+x.getPortInfoCreateOn());
		});
		
		Collections.reverse(listOfPorts);
		return portInfoOrganizer.organizeThePortInfo(listOfPorts);
	//null	return new PortInformation(openPorts);
	}

	private Comparator<Port> getDateComparator() {
		Comparator<Port> comparatorByY = new SortPortsByYearComparator();
		Comparator<Port> comparatorByYM = comparatorByY.thenComparing(new SortPortsByMonthComparator());
		Comparator<Port> comparatorByYMD = comparatorByYM.thenComparing(new SortPortsByDateComparator());
		Comparator<Port> comparatorByYMDH = comparatorByYMD.thenComparing(new SortPortsByHourComparator());
		Comparator<Port> comparatorByYMDHm = comparatorByYMDH.thenComparing(new SortPortsByMinuteComparator());
		Comparator<Port> comparatorByYMDHms = comparatorByYMDHm.thenComparing(new SortPortsBySecondComparator());
		return comparatorByYMDHms;
	}
	
	private String[] clean(String[] tempOutput) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tempOutput.length; i++) {
			if(!tempOutput[i].trim().equals("")) {
				sb.append(tempOutput[i].trim()+"#");
			}
		}
		return sb.toString().split("#");
	}

}
