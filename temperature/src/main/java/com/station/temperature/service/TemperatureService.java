package com.station.temperature.service;

import com.station.temperature.model.ReturnModel;


public interface TemperatureService {
	
	public ReturnModel getBetweenDatastamp(String startTime, String endTime);
	
}
