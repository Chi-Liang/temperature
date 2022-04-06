package com.station.temperature.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.station.temperature.constant.ResultType;
import com.station.temperature.dao.TemperatureRepository;
import com.station.temperature.entity.Temperature;
import com.station.temperature.model.AvgTemperatureModel;
import com.station.temperature.model.ReturnModel;
import com.station.temperature.service.TemperatureService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemperatureSerivceImpl implements TemperatureService {

	@Autowired
	private TemperatureRepository temperatureRepository;

	private static List<String> stationList = Arrays.asList("A", "B");

	@Override
	public ReturnModel getBetweenDatastamp(String startTime, String endTime) {

		ReturnModel returnModel = null;

		try {
			
			Instant queryStartTime = Instant.now();
			
			List<Temperature> temperatureList = temperatureRepository.getBetweenDatastamp(startTime, endTime);
			List<AvgTemperatureModel> avgTemperatureList = new ArrayList<>();
			int batchSize = 1800;
			long count = temperatureList.size() / stationList.size();
			long limit = (count + batchSize - 1) / batchSize;

			stationList.forEach(s -> {
				List<Temperature> tempList = temperatureList.stream().filter(t -> s.equals(t.getStation()))
						.collect(Collectors.toList());
				List<Double> averageList = new ArrayList<Double>();
				Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
					Double avg = tempList.stream().skip(i * batchSize).limit(batchSize)
							.mapToDouble(t -> Double.valueOf(t.getValue())).average().getAsDouble();
					averageList.add(avg);

				});
				avgTemperatureList.add(new AvgTemperatureModel(s,averageList));
			});
			
			Instant queryEndTime = Instant.now();
			Duration duration = Duration.between(queryStartTime, queryEndTime);
			log.info("花費時間  :" + duration.toMillis());
			
			returnModel = new ReturnModel(ResultType.success, "", avgTemperatureList);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			returnModel = new ReturnModel(ResultType.fail, e.getMessage(), null);
		}

		return returnModel;
	}

}
