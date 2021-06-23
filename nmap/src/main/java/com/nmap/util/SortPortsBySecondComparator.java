package com.nmap.util;

import java.util.Comparator;

import com.nmap.model.Port;

public class SortPortsBySecondComparator implements Comparator<Port> {
	@Override
	public int compare(Port p1, Port p2) {
		String date1 =  p1.getPortInfoCreateOn();
		String date2 =  p2.getPortInfoCreateOn();
		String[] date1Array = date1.split("-");
		String[] date2Array = date2.split("-");
		return Integer.compare(Integer.valueOf(date1Array[5]),Integer.valueOf(date2Array[5]));
	}


}
