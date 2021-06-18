package com.nmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nmap.modal.PortInformation;
import com.nmap.service.PortService;

@RestController
public class PortController {

	
	@Autowired
	private PortService postService; 
	
	@GetMapping("/open-port/{hostName}")
	public PortInformation getOpenPort(@PathVariable String hostName) {
		return postService.getOpenPortsByHostName(hostName);
	}
}
