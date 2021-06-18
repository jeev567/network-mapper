package com.nmap.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nmap.constant.NmapperConstant;
import com.nmap.data.PortRepo;
import com.nmap.modal.Port;
import com.nmap.modal.PortInformation;
import com.nmap.service.PortService;

@Service
public class PortServiceImpl implements PortService {

	@Autowired
	private PortRepo portRepository;

	public PortInformation getOpenPortsByHostName(String hostName) {
		return getAllThePortInformation(NmapperConstant.GET_OPEN_PORT_COMMAND + " " + hostName);
	}

	private PortInformation getAllThePortInformation(String command) {

		// Regex usage to figure out open ports from terminal output
		Pattern r = Pattern.compile(NmapperConstant.PATTERN_FOR_PORT);
		Process process = null;
		List<Port> openPorts = new ArrayList<Port>();
		StringBuffer stringBuffer = new StringBuffer();
		boolean ignoreFirst = false;
		try {
			process = Runtime.getRuntime().exec(command);
			System.out.println("Please wait ...");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), NmapperConstant.UTF8.toString()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line + "\n");
				if (r.matcher(line).find()) {
						if(!ignoreFirst) {
							ignoreFirst = true;
						}else {
							String[] tempOutput = stringBuffer.toString().split(" ");
							tempOutput = clean(tempOutput);
							// TODO: check if the size is three;
							Port p = new Port(tempOutput[0].trim().split("/")[0],tempOutput[0].trim().split("/")[1], tempOutput[1].trim(), tempOutput[2].trim()
									 );
							openPorts.add(p);
						}
				}
				stringBuffer = new StringBuffer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new PortInformation(openPorts);
	}

	private String[] clean(String[] tempOutput) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tempOutput.length; i++) {
			if(!tempOutput[i].trim().equals("")) {
				sb.append(tempOutput[i].trim()+"#");
			}
		}
		return sb.toString().split("#");
	}

}
