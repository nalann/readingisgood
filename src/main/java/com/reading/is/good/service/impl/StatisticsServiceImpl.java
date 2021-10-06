package com.reading.is.good.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.reading.is.good.common.Utils;
import com.reading.is.good.dto.Statistics;
import com.reading.is.good.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<Statistics> getStatistics(String email) {
		MatchOperation match = Aggregation.match(Criteria.where("email").is(email));
		ProjectionOperation project1 = Aggregation.project("orderDate")
				.and(ConvertOperators.ToDate.toDate("$orderDate")).as("dateFormat").and("detail").as("detail");
		ProjectionOperation project2 = Aggregation.project("dateFormat").andExpression("month(dateFormat)").as("month")
				.and("detail").as("detail");
		GroupOperation group1 = Aggregation.group("month").push("detail").as("detail").push("month").as("monthParam");
		UnwindOperation unwind1 = Aggregation.unwind("detail");
		UnwindOperation unwind2 = Aggregation.unwind("detail");
		GroupOperation group2 = Aggregation.group("month").count().as("totalOrderCount").sum("detail.totalPrice").as("totalAmount")
				.sum("detail.bookOrderCount").as("totalBookCount").push("monthParam")
				.as("monthParam");
		ProjectionOperation project3 = Aggregation.project("totalBookCount", "totalOrderCount", "totalAmount")
				.and(ArrayOperators.ArrayElemAt.arrayOf("monthParam").elementAt(0)).as("monthParam");
		ProjectionOperation project4 = Aggregation.project("totalBookCount", "totalOrderCount", "totalAmount")
				.and(ArrayOperators.ArrayElemAt.arrayOf("monthParam").elementAt(0)).as("month");

		Aggregation agg = Aggregation.newAggregation(match, project1, project2, group1, unwind1, unwind2, group2,
				project3, project4);

		AggregationResults<Statistics> results = mongoTemplate.aggregate(agg, "order", Statistics.class);
		
		results.getMappedResults().forEach(stats -> {
			int month = Integer.parseInt(stats.getMonth());
			stats.setMonth(Utils.convertMonthName(month));
		});

		return results.getMappedResults();
	}

}
