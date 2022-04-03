package com.station.temperature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.Test;
import com.station.temperature.constant.ResultType;
import com.station.temperature.entity.Temperature;
import com.station.temperature.model.ReturnModel;

public class TemperatureUnitTest extends Abstract {

	@Test
	public void insertDB() {
		
		String fileName = "insertDB.xlsx";
		boolean result = insertDBSericeImpl.insertDB(fileName);
		assertEquals(result, true);
		
	}
	
	@Test
	public void testGetBetweenDatastampDao() {
		
		String startTime = "2021-12-28 15:10:10";
		String endTime = "2021-12-28 15:11:10";
		
		List<Temperature> temperatureList = temperatureRepository.getBetweenDatastamp(startTime, endTime);
		assertEquals(120, temperatureList.size());
		
	}
	
	@Test
	public void testGetBetweenDatastampService() {
		
		String startTime = "2021-12-28 15:10:10";
		String endTime = "2021-12-28 15:11:10";
		
		ReturnModel returnModel = temperatureSerivceImpl.getBetweenDatastamp(startTime, endTime);
		assertEquals(ResultType.success, returnModel.getResult());
		
	}
	

}
