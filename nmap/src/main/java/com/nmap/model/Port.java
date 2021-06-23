package com.nmap.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "port")
public class Port {

	String hostname;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	String portNumber;
	String portProtocol;
	String portStatus;
	String portSerive;
	String portInfoCreateOn;
	Integer latest;

	public Port() {
		super();
	}

	public Port(String portNumber, String portProtocol, String portStatus, String portSerive, String hostname,
			String portInfoCreateOn, Integer latest) {
		super();
		this.hostname = hostname;
		this.portNumber = portNumber;
		this.portProtocol = portProtocol;
		this.portStatus = portStatus;
		this.portSerive = portSerive;
		this.portInfoCreateOn = portInfoCreateOn;
		this.latest = latest;
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

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
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

	public String getPortInfoCreateOn() {
		return portInfoCreateOn;
	}

	public void setPortInfoCreateOn(String portInfoCreateOn) {
		this.portInfoCreateOn = portInfoCreateOn;
	}

	public Integer getLatest() {
		return latest;
	}

	public void setLatest(Integer latest) {
		this.latest = latest;
	}

	@Override
	public String toString() {
		return "Port [hostname=" + hostname + ", id=" + id + ", portNumber=" + portNumber + ", portProtocol="
				+ portProtocol + ", portStatus=" + portStatus + ", portSerive=" + portSerive + ", portInfoCreateOn="
				+ portInfoCreateOn + ", latest=" + latest + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((portNumber == null) ? 0 : portNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Port other = (Port) obj;
		if (portNumber == null) {
			if (other.portNumber != null)
				return false;
		} else if (!portNumber.equals(other.portNumber))
			return false;
		return true;
	}

}
