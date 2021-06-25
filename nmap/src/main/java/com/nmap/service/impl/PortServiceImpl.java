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
	private PortFetchLatestScanRepo portFetchLatestScanRepo;
	
	@Autowired
	private PortInfoOrganizer portInfoOrganizer;
	
	@Value("${port.scan.history.threshold}")
	private String threshold;

	public PortInformation getOpenPortsByHostName(String hostName) {
		return getAllThePortInformation(hostName);
	}

	private PortInformation getAllThePortInformation(String hostName) {
		// fetch latest scanId by hostname
		List<Port> listOfPorts = new ArrayList<>();
		try {
			// Runs NMAP Command
			System.out.println("Please wait ...");
			Process process = Runtime.getRuntime().exec(NmapperConstant.GET_OPEN_PORT_COMMAND + " " + hostName);
			List<PortFetchLatestScan> portFetchLatestScanList = portFetchLatestScanRepo.findByHostname(hostName);
			Integer latestScanId = 0;
			if(portFetchLatestScanList.size()>0 && portFetchLatestScanList.get(0)!=null) {
				latestScanId = portFetchLatestScanList.get(0).getLatestScanId()+1;
			}
			
			parsaDataAndSave(hostName, latestScanId, process);
			
			updateLatestScanId(hostName,portFetchLatestScanList, latestScanId);
			
			latestScanId = (latestScanId- Integer.valueOf(threshold))+1;
			latestScanId = (latestScanId < 0) ? 0 : latestScanId; 
			
			listOfPorts = portRepository.findByHostnameAndScanIdGreaterThanEqual(hostName,latestScanId);		
			
			Collections.reverse(listOfPorts);

			listOfPorts.stream().forEach(port->{
				System.out.println(port.getId()+" -- "+ port.getHostname()+ " -- " +port.getScanId());
			});
		} catch(IOException e) {
			e.printStackTrace();
		}
		return portInfoOrganizer.organizeThePortInfo(listOfPorts);
	}
	
	private void updateLatestScanId(String hostName, List<PortFetchLatestScan> portFetchLatestScanList, Integer latestIndex) {
		if(portFetchLatestScanList.size()>0 && portFetchLatestScanList.get(0)!=null) {
			latestIndex = portFetchLatestScanList.get(0).getLatestScanId()+1;
			PortFetchLatestScan portFetchLatestScan = portFetchLatestScanList.get(0);
			portFetchLatestScan.setLatestScanId(latestIndex);
			portFetchLatestScanRepo.save(portFetchLatestScan);
		}else {
			PortFetchLatestScan portFetchThreshold = new PortFetchLatestScan(hostName, latestIndex);
			portFetchLatestScanRepo.save(portFetchThreshold);
		}
	}
	
	private void parsaDataAndSave(String hostName, Integer latestIndex, Process process) {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), NmapperConstant.UTF8.toString()));
			String line = null;
			StringBuffer stringBuffer = new StringBuffer();
			Pattern pattern = Pattern.compile(NmapperConstant.PATTERN_FOR_PORT);
			
			// Parses the data from NMAP and saves them in portRepository
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line + "\n");
				if (pattern.matcher(line).find()) {
					String[] tempOutput = stringBuffer.toString().split(" ");
					tempOutput = clean(tempOutput);
					Port port = new Port(tempOutput[0].trim().split("/")[0],tempOutput[0].trim().split("/")[1], tempOutput[1].trim(), tempOutput[2].trim()
							,hostName.toLowerCase(),latestIndex);
					portRepository.save(port);
				}
				stringBuffer = new StringBuffer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
