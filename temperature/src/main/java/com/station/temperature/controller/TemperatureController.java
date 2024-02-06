package com.station.temperature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.station.temperature.model.ReturnModel;
import com.station.temperature.model.TimeIntervalModel;
import com.station.temperature.service.impl.TemperatureSerivceImpl;

@RestController
@RequestMapping("/api")
public class TemperatureController {
	
	@Autowired
	private TemperatureSerivceImpl temperatureSerivceImpl;
	
	@PostMapping("/temperature/average")
	public ReturnModel findCategoryByMember(@RequestBody TimeIntervalModel timeIntervalModel) {
		System.out.println("aaaaaaaaaa");
		return temperatureSerivceImpl.getBetweenDatastamp(timeIntervalModel.getStartTime(), timeIntervalModel.getEndTime());
	}
	
}
