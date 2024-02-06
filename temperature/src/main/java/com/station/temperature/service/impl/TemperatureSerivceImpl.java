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

	private static List<String> stationList = Arrays.asList("A","B","C","D","E");

	@Override
	public ReturnModel getBetweenDatastamp(String startTime, String endTime) {

		ReturnModel returnModel = null;

		try {
			
			Instant queryStartTime11 = Instant.now();
			temperatureRepository.getBetweenDatastamp2();
			Instant queryEndTime11 = Instant.now();
			Duration duration11 = Duration.between(queryStartTime11, queryEndTime11);
			log.info("花費時間  :" + duration11.toMillis());
			
			
			Instant queryStartTime = Instant.now();
			List<Temperature> temperatureList = temperatureRepository.getBetweenDatastamp(startTime, endTime);
			
//			Instant queryStartTime2 = Instant.now();
//			List<Object> temperatureList2 = temperatureRepository.getBetweenDatastamp1(startTime, endTime);
//			Instant queryEndTime2 = Instant.now();
//			Duration duration2 = Duration.between(queryStartTime2, queryEndTime2);
//			log.info("花費時間2  :" + duration2.toMillis());
			
			List<AvgTemperatureModel> avgTemperatureList = new ArrayList<>();
			int batchSize = 1800;
			long count = temperatureList.size() / stationList.size();
			long limit = (count + batchSize - 1) / batchSize;
			
//			Instant queryStartTime1 = Instant.now();
			
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
			Duration duration22 = Duration.between(queryStartTime, queryEndTime);
			log.info("花費時間  :" + duration22.toMillis());
			
//			Instant queryEndTime1 = Instant.now();
//			Duration duration1 = Duration.between(queryStartTime1, queryEndTime1);
//			log.info("花費時間3  :" + duration1.toMillis());
//			
//			Instant queryStartTime3 = Instant.now();
//			List<Double> averageList1 = new ArrayList<Double>();
//			stationList.forEach(s -> {
//				List<Temperature> tempList = temperatureList.stream().filter(t -> s.equals(t.getStation()))
//						.collect(Collectors.toList());
//				
//				List<Temperature> aa = new ArrayList<>();
//				int a = 0;
//				for (Temperature temperature : tempList) {
//					aa.add(temperature);
//					a++;
//					if(a % 1800 == 0) {
//						Double avg = aa.stream().mapToDouble(t ->Double.valueOf(t.getValue())).average().getAsDouble();
//						averageList1.add(avg);
//						aa = new ArrayList<Temperature>();
//					}
//				}
//				
//			});
//			
//			Instant queryEndTime3 = Instant.now();
//			Duration duration3 = Duration.between(queryStartTime3, queryEndTime3);
//			log.info("花費時間 4 :" + duration3.toMillis());
//			
//			Instant queryStartTime5 = Instant.now();
//			List<Double> averageList2 = new ArrayList<Double>();
//			for (String s : stationList) {
//				double sum = 0.0;
//				int a = 0;
//				for (Temperature t : temperatureList) {
//					if(t.getStation().equals(s)) {
//						a++;
//						sum += Double.valueOf(t.getValue());
//						if(a % 1800 == 0) {
//							double avg = sum / 1800.0;
//							averageList2.add(avg);
//							sum = 0.0;
//						}
//					}
//				}
//			}
//			Instant queryEndTime5 = Instant.now();
//			Duration duration5 = Duration.between(queryStartTime5, queryEndTime5);
//			log.info("花費時間 5 :" + duration5.toMillis());
			
			returnModel = new ReturnModel(ResultType.success, "", avgTemperatureList);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			returnModel = new ReturnModel(ResultType.fail, e.getMessage(), null);
		}

		return returnModel;
	}

}
