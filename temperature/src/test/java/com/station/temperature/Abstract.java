package com.station.temperature;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.station.temperature.dao.TemperatureRepository;
import com.station.temperature.service.InsertDBSerice;
import com.station.temperature.service.impl.TemperatureSerivceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Abstract {
	
	@Autowired
	protected InsertDBSerice insertDBSericeImpl;
	
	@Autowired
	protected TemperatureRepository temperatureRepository;
	
	@Autowired
	protected TemperatureSerivceImpl temperatureSerivceImpl;
}
