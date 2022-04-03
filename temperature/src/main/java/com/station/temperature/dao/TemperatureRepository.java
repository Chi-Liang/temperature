package com.station.temperature.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.station.temperature.entity.Temperature;

@Repository
public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {

	@Query(nativeQuery = true, value = "select * from temperature where datastamp >= :startTime and datastamp < :endTime ")
	List<Temperature> getBetweenDatastamp(@Param("startTime") String startTime,@Param("endTime") String endTime);
}
