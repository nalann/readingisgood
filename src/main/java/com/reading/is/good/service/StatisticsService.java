package com.reading.is.good.service;

import java.util.List;

import com.reading.is.good.dto.Statistics;

public interface StatisticsService {
	
	List<Statistics> getStatistics(String email);

}
