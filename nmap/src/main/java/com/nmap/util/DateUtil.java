package com.nmap.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;


@Component
public class DateUtil {
	public String getCurrentDateTimeInUTCStringFormat() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(NmapperConstant.DATE_TIME_FORMAT_FOR_SORTING.toString())
				.withZone(ZoneId.from(ZoneOffset.UTC));
		Instant instant = Instant.now();
		return formatter.format(instant);
	}
}
