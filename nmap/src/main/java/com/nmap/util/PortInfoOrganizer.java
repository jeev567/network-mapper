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

		PortInformation portInformation = new PortInformation();
		portInformation.setHostName("Unknown Host");
		
		List<Port> latestScannedPorts = new ArrayList<Port>();
		List<List<Port>> historyScannedPort = new ArrayList<List<Port>>();
		List<Port> newlyAddedPorts = new ArrayList<Port>();
		List<Port> deletedPorts = new ArrayList<Port>();
		
		portInformation.setDeletedPorts(deletedPorts);
		portInformation.setNewlyAddedPorts(newlyAddedPorts);
		portInformation.setLatestScannedPorts(latestScannedPorts);
		portInformation.setHistoryScannedPorts(historyScannedPort);
		
		// Adds latest scanned ports and history ports
		List<Port> secondLatestScan = new ArrayList<Port>();
		boolean isSecondScanned=false;
		if (ports.size() > 0) {
			portInformation.setHostName(ports.get(0).getHostname());
			int currentScanId = ports.get(0).getScanId();
			for (Port port : ports) {
				if (port.getScanId() != currentScanId) {
					currentScanId = port.getScanId();
					latestScannedPorts = new ArrayList<Port>();
					if(!isSecondScanned) {
						isSecondScanned=true;
						secondLatestScan = latestScannedPorts;
					}
					historyScannedPort.add(latestScannedPorts);
					latestScannedPorts.add(port);
				} else {
					latestScannedPorts.add(port);
				}
			}
		}
		
		// Adds newly added ports and deleted ports 
		List<Port> latestScanPorts;
		if(portInformation.getHistoryScannedPorts().size() > 0) {
			latestScanPorts = portInformation.getLatestScannedPorts();
			Set<Port> reference = new HashSet<Port>();
			if (latestScanPorts.size() > secondLatestScan.size()) {
				secondLatestScan.forEach(x->reference.add(x));
				for (Port port : latestScanPorts) {
					if (!reference.contains(port)) {
						newlyAddedPorts.add(port);
					}
				}
			} else if (latestScanPorts.size() < secondLatestScan.size()) {
				latestScanPorts.forEach(x->reference.add(x));
				for (Port port : secondLatestScan) {
					if (!reference.contains(port)) {
						deletedPorts.add(port);
					}
				}
			}
		}
		return portInformation;
	}
}
