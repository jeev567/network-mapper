package com.nmap.util;

public interface NmapperConstant {
	
	//General
	String UTF8 = "UTF-8";
	
	//REGEX
	String PATTERN_FOR_PORT = "[0-9]*/+[a-z]* *open *[a-zA-Z-]*";		
	
	//NMAP CLI command
	String GET_OPEN_PORT_COMMAND = "nmap";
	
}
