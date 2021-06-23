package com.nmap.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.nmap.model.Port;
import com.nmap.model.PortInformation;

@Component
public class PortInfoOrganizer {

	public PortInformation organizeThePortInfo(List<Port> ports) {

		PortInformation finalOutPut = new PortInformation();
		finalOutPut.setHostName("Unknown Host");
		
		List<Port> latestScannedPorts = new ArrayList<Port>();
		List<List<Port>> historyScannedPort = new ArrayList<List<Port>>();
		List<Port> newlyAddedPorts = new ArrayList<Port>();
		List<Port> deletedPorts = new ArrayList<Port>();
		
		finalOutPut.setDeletedPorts(deletedPorts);
		finalOutPut.setNewlyAddedPorts(newlyAddedPorts);
		finalOutPut.setLatestScannedPorts(latestScannedPorts);
		finalOutPut.setHistoryScannedPorts(historyScannedPort);
		
		
		List<Port> secondLatestbucket = new ArrayList<Port>();
		boolean isSecondScanned=false;
		if (ports.size() > 0) {
			finalOutPut.setHostName(ports.get(0).getHostname());
			String currentBucket = ports.get(0).getPortInfoCreateOn();
			for (Port port : ports) {
				
				if (!port.getPortInfoCreateOn().equals(currentBucket)) {
					currentBucket = port.getPortInfoCreateOn();
					latestScannedPorts = new ArrayList<Port>();
					if(!isSecondScanned) {
						isSecondScanned=true;
						secondLatestbucket = latestScannedPorts;
					}
					historyScannedPort.add(latestScannedPorts);
					latestScannedPorts.add(port);
				} else {
					latestScannedPorts.add(port);
				}
			}
		}
		
		
		//Calculate the differntial
		//New ports Added
		List<Port> latest  = finalOutPut.getLatestScannedPorts();
		//latest.remove(1);
		
		if(finalOutPut.getHistoryScannedPorts().size()>0) {
			latest = finalOutPut.getLatestScannedPorts();
			Set<Port> reference = new HashSet<Port>();
			if (latest.size() > secondLatestbucket.size()) {
				secondLatestbucket.forEach(x->reference.add(x));
				for (Port port : latest) {
					if (!reference.contains(port)) {
						deletedPorts.add(port);
					}
				}
			} else if (latest.size() < secondLatestbucket.size()) {
				latest.forEach(x->reference.add(x));
				for (Port port : secondLatestbucket) {
					if (!reference.contains(port)) {
						newlyAddedPorts.add(port);
					}
				}
			}
		}
		return finalOutPut;
	}

}
