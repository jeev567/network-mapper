package com.nmap.modal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PortFetchThreshold")
public class PortFetchThreshold {

	@Id
	private String hostname;
	public PortFetchThreshold(String hostname, Integer latest) {
		super();
		this.hostname = hostname;
		this.latest = latest;
	}

	public PortFetchThreshold() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Integer latest;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getLatest() {
		return latest;
	}

	public void setLatest(Integer latest) {
		this.latest = latest;
	}

}
