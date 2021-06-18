package com.nmap.modal;

import java.util.List;

public class PortInformation {

	private String hostName;
	private List<Port> latestScannedPorts;
	private List<List<Port>> historyScannedPort;
	
	public PortInformation() {
		super();
	}
	
	public List<List<Port>> getHistoryScannedPort() {
		return historyScannedPort;
	}

	public void setHistoryScannedPort(List<List<Port>> historyScannedPort) {
		this.historyScannedPort = historyScannedPort;
	}

	public List<Port> getLatestScannedPorts() {
		return latestScannedPorts;
	}

	public void setLatestScannedPorts(List<Port> latestScannedPorts) {
		this.latestScannedPorts = latestScannedPorts;
	}


	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	

}
