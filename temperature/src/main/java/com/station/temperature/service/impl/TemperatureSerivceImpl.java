package com.station.temperature.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Row;
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
		
		try {
			List<String> temperatureList = temperatureRepository.getBetweenDatastamp(startTime, endTime);
			List<Double> resultList = new ArrayList<Double>();

			int batchSize = 1800;
			long count = temperatureList.size();
			long limit = (count + batchSize - 1) / batchSize;
			Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {

				Double avg = temperatureList.stream().skip(i * batchSize).limit(batchSize)
						.mapToDouble(t -> Double.valueOf(t)).average().getAsDouble();
				resultList.add(avg);

			});

			returnModel = new ReturnModel(ResultType.success, "", resultList);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			returnModel = new ReturnModel(ResultType.fail, e.getMessage(), null);
		}

		return returnModel;
	}

}
