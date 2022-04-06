package com.station.temperature.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvgTemperatureModel {
	
	private String station;
	private List<Double> averageList;
	
}
