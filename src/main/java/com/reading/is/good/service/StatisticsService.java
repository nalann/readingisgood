package com.reading.is.good.service;

import java.util.ArrayList;

import org.bson.Document;

public interface StatisticsService {
	
	ArrayList<Document> getStatistics(String email);

}
