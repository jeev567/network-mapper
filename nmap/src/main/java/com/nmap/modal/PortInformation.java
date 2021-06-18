package com.nmap.modal;

import java.util.List;

public class PortInformation {

	private List<Port> openPorts;

	public PortInformation(List<Port> openPorts) {
		super();
		this.openPorts = openPorts;
	}

	public List<Port> getOpenPorts() {
		return openPorts;
	}

	public void setOpenPorts(List<Port> openPorts) {
		this.openPorts = openPorts;
	}

}
