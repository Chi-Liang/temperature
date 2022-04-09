package com.station.temperature.service.impl;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.station.temperature.dao.TemperatureRepository;
import com.station.temperature.entity.Temperature;
import com.station.temperature.service.InsertDBSerice;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InsertDBSericeImpl implements InsertDBSerice {
	
	@Autowired
	private TemperatureRepository temperatureRepository;
	
	private static List<String> stationList = Arrays.asList("A","B","C","D","E");
	
	@Override
	public boolean insertDB(String fileName) {

		String filePath = this.getClass().getClassLoader().getResource(fileName).getPath();
		boolean result = false;

		try (var inputStream = new FileInputStream(filePath); var wb = new XSSFWorkbook(inputStream);) {
			Instant startTime = Instant.now();
			
			stationList.forEach(s -> {
				parseExcel(wb,s);
			});
			
			
			Instant endTime = Instant.now();
			Duration duration = Duration.between(startTime, endTime);
			log.info("花費時間  :" + duration.toMillis());
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		return result;
	}

	public void parseExcel(Workbook workbook,String s) {
		
		int sheetNum = 0;
		Sheet sheet = workbook.getSheetAt(sheetNum);

		int rowEnd = sheet.getPhysicalNumberOfRows();
		int batchSize = 1000;
		long count = rowEnd - 1;
		long limit = (count + batchSize - 1) / batchSize;
		
		Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
			
			int start = i * batchSize + 1;
			int end = start + batchSize;
			List<Temperature> temperatureLsit = new ArrayList<Temperature>();
			
			for (int rownum = start; rownum < end; rownum++) {
				Row row = sheet.getRow(rownum);
				if (row == null) {
					continue;
				}
				Temperature temperature = convertRowToData(row,s);
				temperatureLsit.add(temperature);
			}
			temperatureRepository.saveAll(temperatureLsit);
			
		});
	}

	private Temperature convertRowToData(Row row,String s) {

		Temperature temperature = new Temperature();

		Cell cell;
		int cellNum = 0;

		cell = row.getCell(cellNum++);
		Date datastamp = cell.getDateCellValue();
		temperature.setDatastamp(datastamp);

		cell = row.getCell(cellNum++);
		Double doubleValue = cell.getNumericCellValue();
		String ms = String.valueOf(doubleValue);
		temperature.setMs(ms);

		cell = row.getCell(cellNum++);
		
		String value = String.valueOf(cell.getNumericCellValue());
		temperature.setValue(value);
		temperature.setStation(s);
		return temperature;
	}

}
