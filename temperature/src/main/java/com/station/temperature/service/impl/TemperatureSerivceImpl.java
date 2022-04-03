package com.station.temperature.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.station.temperature.constant.ResultType;
import com.station.temperature.dao.TemperatureRepository;
import com.station.temperature.entity.Temperature;
import com.station.temperature.model.ReturnModel;
import com.station.temperature.service.TemperatureService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemperatureSerivceImpl implements TemperatureService {

	@Autowired
	private TemperatureRepository temperatureRepository;

	@Override
	public ReturnModel getBetweenDatastamp(String startTime, String endTime) {
		
		ReturnModel returnModel = null;
		
		try{
			List<Temperature> temperatureList = temperatureRepository.getBetweenDatastamp(startTime, endTime);
			List<Double> aa = null;
			returnModel = new ReturnModel(ResultType.success,"",aa);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			returnModel = new ReturnModel(ResultType.fail,e.getMessage(),null);
		}

		return returnModel;
	}

}
