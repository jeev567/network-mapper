package com.nmap.service;

import org.springframework.stereotype.Service;

import com.nmap.model.PortInformation;


public interface PortService {
	public  PortInformation getOpenPortsByHostName(String hostName);
	

}
