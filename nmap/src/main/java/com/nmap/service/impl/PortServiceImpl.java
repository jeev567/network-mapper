package com.nmap.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nmap.data.PortRepo;
import com.nmap.data.PortFetchLatestScanRepo;
import com.nmap.model.Port;
import com.nmap.model.PortFetchLatestScan;
import com.nmap.model.PortInformation;
import com.nmap.service.PortService;
import com.nmap.util.NmapperConstant;
import com.nmap.util.PortInfoOrganizer;

@Service
public class PortServiceImpl implements PortService {

	@Autowired
	private PortRepo portRepository;
	
	@Autowired
	private PortFetchLatestScanRepo portThresholdRepo;
	
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
		
		List<PortFetchLatestScan> portFetchThresholds = portThresholdRepo.findByHostname(hostName);
		Integer latestIndex = 0;
		if(portFetchThresholds.size()>0 && portFetchThresholds.get(0)!=null) {
			latestIndex = portFetchThresholds.get(0).getLatestScanId()+1;
			PortFetchLatestScan out = portFetchThresholds.get(0);
			out.setLatestScanId(latestIndex);
			portThresholdRepo.save(out);
		}else {
			PortFetchLatestScan portFetchThreshold = new PortFetchLatestScan(hostName, latestIndex);
			portThresholdRepo.save(portFetchThreshold);
		}
		
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
							,hostName.toLowerCase(),latestIndex);
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
		
		List<Port> listOfPorts = portRepository.findByHostnameAndScanIdGreaterThanEqual(hostName,latestIndex);
		
		Collections.reverse(listOfPorts);
		listOfPorts.stream().forEach(x->{
			System.out.println(x.getId()+"--"+x.getScanId());
		});
		return portInfoOrganizer.organizeThePortInfo(listOfPorts);
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
