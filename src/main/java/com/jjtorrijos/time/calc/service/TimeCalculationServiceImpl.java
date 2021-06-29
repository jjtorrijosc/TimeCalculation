package com.jjtorrijos.time.calc.service;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimeCalculationServiceImpl implements TimeCalculationService {

	public long minutesBetween(Date time1, Date time2) {
		/*
		 * 1. Date.getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT,
		 * 						no need to worry anymore about the time zones
		 * 2. time1.getTime() - time2.getTime() = difference between both dates in milliseconds,
		 * 			it can be a negative value if time2 is greater than time1, apply Absolute Value
		 * 3. transform to Minutes. 60 seconds = 1 minute, 1000 milliseconds = 1 second
		 * 			rounded down because long cast truncate decimal content
		 */
		return Math.abs(time1.getTime()-time2.getTime())/60000; 
	}
	
}
