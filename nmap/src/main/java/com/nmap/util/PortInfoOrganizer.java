package com.nmap.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nmap.modal.Port;
import com.nmap.modal.PortInformation;

@Component
public class PortInfoOrganizer {

	public PortInformation organizeThePortInfo(List<Port> raw) {

		PortInformation finalOutPut = new PortInformation();
		finalOutPut.setHostName("Unknown Host");
		if (raw.size() > 0) {
			finalOutPut.setHostName(raw.get(0).getHostname());
			String currentBucket = raw.get(0).getPortInfoCreateOn();
			List<Port> bucket = new ArrayList<Port>();
			List<List<Port>> historybucket = new ArrayList<List<Port>>();
			finalOutPut.setLatestScannedPorts(bucket);
			finalOutPut.setHistoryScannedPort(historybucket);
			for (Port port : raw) {

				if (!port.getPortInfoCreateOn().equals(currentBucket)) {
					currentBucket = port.getPortInfoCreateOn();
					bucket = new ArrayList<Port>();
					historybucket.add(bucket);
					bucket.add(port);
				} else {
					bucket.add(port);
				}
			}
		}
		return finalOutPut;

	}

}
