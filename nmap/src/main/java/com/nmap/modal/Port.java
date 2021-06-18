package com.nmap.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "port")
public class Port {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String portNumber;
	String portProtocol;
	String portStatus;
	String portSerive;

	public Port() {
		super();
	}

	public Port(String portNumber, String portProtocol, String portStatus, String portSerive) {
		super();
		this.portNumber = portNumber;
		this.portProtocol = portProtocol;
		this.portStatus = portStatus;
		this.portSerive = portSerive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getPortProtocol() {
		return portProtocol;
	}

	public void setPortProtocol(String portProtocol) {
		this.portProtocol = portProtocol;
	}

	public String getPortStatus() {
		return portStatus;
	}

	public void setPortStatus(String portStatus) {
		this.portStatus = portStatus;
	}

	public String getPortSerive() {
		return portSerive;
	}

	public void setPortSerive(String portSerive) {
		this.portSerive = portSerive;
	}

	@Override
	public String toString() {
		return "Port [portNumber=" + portNumber + ", portProtocol=" + portProtocol + ", portStatus=" + portStatus
				+ ", portSerive=" + portSerive + "]";
	}

}
