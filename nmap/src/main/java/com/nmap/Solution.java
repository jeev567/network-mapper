package com.nmap;

public class Solution {
	public static void main(String[] args) {

		String input = "Akansha";
		System.out.println(reverse(input));

	}

	private static String reverse(String input) {
		int start = 0;
		int end = input.length() - 1;
		char[] i = input.toCharArray();
		while (start < end) {
			char temp = i[end];
			i[end] = i[start];
			i[start] = temp;
			start++;
			end--;
		}
		return String.valueOf(i);
	}
}
