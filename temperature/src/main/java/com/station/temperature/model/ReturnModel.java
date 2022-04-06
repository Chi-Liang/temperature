package com.station.temperature.model;

import java.util.List;
import com.station.temperature.constant.ResultType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReturnModel {
	
	private ResultType result;
	private String message;
	private List<AvgTemperatureModel> avgTemperatureList;
	
}
