package com.station.temperature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.station.temperature.constant.ResultType;
import com.station.temperature.entity.Temperature;
import com.station.temperature.model.ReturnModel;
import com.station.temperature.model.TimeIntervalModel;

public class TemperatureUnitTest extends AbstractTest {

	@Test
	public void insertDB() {
		
		String fileName = "insertDB.xlsx";
		boolean result = insertDBSericeImpl.insertDB(fileName);
		assertEquals(result, true);
		
	}
	
	@Test
	public void testRestApi() throws Exception {
		
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		String jsonStr = new ObjectMapper()
				.writeValueAsString(new TimeIntervalModel("2021-12-28 15:00:00", "2021-12-28 15:30:00"));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/temperature/average").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	
	@Test
	public void testGetBetweenDatastampDao() {
		
		String startTime = "2021-12-28 15:00:00";
		String endTime = "2021-12-28 15:30:00";
		List<Temperature> temperatureList = temperatureRepository.getBetweenDatastamp(startTime, endTime);
		assertEquals(18000, temperatureList.size());
		
	}
	
	@Test
	public void testGetBetweenDatastampService() {
		
		String startTime = "2021-12-28 00:00:00";
		String endTime = "2021-12-29 00:00:00";
		ReturnModel returnModel = temperatureSerivceImpl.getBetweenDatastamp(startTime, endTime);
		assertEquals(ResultType.success, returnModel.getResult());
		
	}
	

}
