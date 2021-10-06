package com.reading.is.good.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reading.is.good.dto.Statistics;
import com.reading.is.good.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsRestService {
	
	@Autowired
	StatisticsService statisticsService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Statistics>> getStatistics(String email) {
		return new ResponseEntity<>(statisticsService.getStatistics(email), HttpStatus.OK);
	}
}
