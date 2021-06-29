package com.jjtorrijos.time.calc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jjtorrijos.time.calc.dto.TimeCalcInputDto;
import com.jjtorrijos.time.calc.service.TimeCalculationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@Api(value = "Time Calculation API")
@RestController
public class TimeCalculationController {

	@Autowired
	TimeCalculationService timeService;
	
	static final SimpleDateFormat sdf_RFC_2822 = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.ENGLISH);
	
	//with GET and requestParams cannot get timezone correctly (char '+'), discard this option
	@RequestMapping(value = "/minutesBetween", method = RequestMethod.POST)
	@ApiOperation(value = "minutesBetween", response=String.class)
	public ResponseEntity<Object> minutesBetween(
										@ApiParam(value = "Object contains time1 and time2 input dates (RFC 2822 format)",required = true)
										@RequestBody(required = true) TimeCalcInputDto inputDto) {
		ResponseEntity<Object> responseEnt = null;
		try {
			responseEnt = ResponseEntity.ok(
						timeService.minutesBetween(
								sdf_RFC_2822.parse(inputDto.getTime1()),
								sdf_RFC_2822.parse(inputDto.getTime2())
						)
					);
		} catch (ParseException e) {
			responseEnt = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format");
		}
		return responseEnt;
	}
	
}
