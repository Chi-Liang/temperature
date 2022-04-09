package com.station.temperature.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.station.temperature.entity.Temperature;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Long> {
	
//	String sql = "select station station,avg(value) avg from temperature where datastamp >= :startTime and datastamp < :endTime " 
//			+  "GROUP BY station, UNIX_TIMESTAMP(datastamp) DIV (15*60)";
	
	
	@Query(nativeQuery = true, value = "select * from temperature where datastamp >= :startTime and datastamp < :endTime order by datastamp ")
	List<Temperature> getBetweenDatastamp(@Param("startTime") String startTime,@Param("endTime") String endTime);
	
//	@Query(nativeQuery = true, value = sql)
//	List<Object> getBetweenDatastamp1(@Param("startTime") String startTime,@Param("endTime") String endTime);

}
