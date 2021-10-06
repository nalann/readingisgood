package com.reading.is.good.rest;

import java.util.ArrayList;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reading.is.good.service.StatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/statistics")
@Api(value = "Statistics Api documentation")
public class StatisticsRestService {
	
	@Autowired
	StatisticsService statisticsService;

	@GetMapping(value = "/all")
	@ApiOperation(value = "Getting Customer Statistics")
	public ResponseEntity<ArrayList<Document>> getStatistics(String email) {
		return new ResponseEntity<>(statisticsService.getStatistics(email), HttpStatus.OK);
	}
}
