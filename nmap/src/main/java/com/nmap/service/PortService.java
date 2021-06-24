package com.nmap.service;

import com.nmap.model.PortInformation;


public interface PortService {
	public  PortInformation getOpenPortsByHostName(String hostName);
	

}
