package com.nmap.model;

import java.util.List;

public class PortInformation {

	private String hostName;
	private List<Port> latestScannedPorts;
	private List<List<Port>> historyScannedPorts;
	private List<Port> newlyAddedPorts;
	private List<Port> deletedPorts;
	
	public PortInformation() {
		super();
	}
	
	public List<List<Port>> getHistoryScannedPorts() {
		return historyScannedPorts;
	}

	public void setHistoryScannedPorts(List<List<Port>> historyScannedPorts) {
		this.historyScannedPorts = historyScannedPorts;
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

	public List<Port> getNewlyAddedPorts() {
		return newlyAddedPorts;
	}

	public void setNewlyAddedPorts(List<Port> newlyAddedPorts) {
		this.newlyAddedPorts = newlyAddedPorts;
	}

	public List<Port> getDeletedPorts() {
		return deletedPorts;
	}

	public void setDeletedPorts(List<Port> deletedPorts) {
		this.deletedPorts = deletedPorts;
	}
	
}
