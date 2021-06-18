package com.nmap.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Solutiotn {

	public static void main(String[] args) throws IOException {

		System.out.println(getReturnData("nmap 127.0.0.1"));

		System.out.println("Done");

	}

	public static String getReturnData(String command) {
		String PATTER_FOR_PORT = "[0-9]*/+[a-z]* *[a-zA-Z]* *[a-zA-Z-]*";
		Pattern r = Pattern.compile(PATTER_FOR_PORT);
		Process process = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			process = Runtime.getRuntime().exec(command);
			System.out.println("Please wait ...");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line + "\n");

				if (r.matcher(line).find()) {
					System.out.println(">>" + stringBuffer.toString());
				}
				System.out.println(">>" + stringBuffer.toString());
				stringBuffer = new StringBuffer();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

}
