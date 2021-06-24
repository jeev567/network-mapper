package com.nmap.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PortFetchLatestScan")
public class PortFetchLatestScan {

	@Id
	private String hostname;
	private Integer latestScanId;

	public PortFetchLatestScan(String hostname, Integer latestScanId) {
		super();
		this.hostname = hostname;
		this.latestScanId = latestScanId;
	}

	public PortFetchLatestScan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getLatestScanId() {
		return latestScanId;
	}

	public void setLatestScanId(Integer latestScanId) {
		this.latestScanId = latestScanId;
	}

}
