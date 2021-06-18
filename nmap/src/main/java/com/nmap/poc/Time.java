package com.nmap.poc;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Time {
	
	  public static void main(String args[]){    
		/*
		 * Date date = new Date(); Timestamp ts=new Timestamp(date.getTime());
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * System.out.println(formatter.format(ts));
		 */
          
          
          
          //An Instant class represents a oment of the timeline in UTC as default timezone.
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss").withZone(ZoneId.from(ZoneOffset.UTC));
                 
		  Instant instant = Instant.now();
		  String instantStr = formatter.format(instant);
		  System.out.println(instantStr);
		  //2021-06-18T03:31:01.673621700Z
		  //2021-06-18T03:31:39.374683900Z
		  //2021-06-18T03:31:48.658750800Z

  }   	
	
	
}
