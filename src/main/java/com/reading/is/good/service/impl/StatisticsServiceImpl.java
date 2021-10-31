package com.reading.is.good.service.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.reading.is.good.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	MongoClient mongoClient;

	@Transactional
	@Override
	public ArrayList<Document> getStatistics(String email) {
		
		/*All statistics calculate with mongodb aggregation.
		* 1. Project month information from order date
		* 2. Group by month
		* 3. unwind by detail
		* 4. Group by month and calculate the "total amount", "total book count", "total order count"
		* 5. Project these information to return monthly statistics information
		*/
		MongoDatabase database = mongoClient.getDatabase("readingisgood");
		MongoCollection<Document> collection = database.getCollection("order");

		AggregateIterable<Document> result = collection
				.aggregate(
						Arrays.asList(
								new Document("$project",
										new Document("month",
												new Document("$month", new Document("$toDate", "$orderDate")))
														.append("detail", "$detail")),
								new Document("$group",
										new Document("_id", new Document("month", "$month"))
												.append("detail", new Document("$push", "$detail"))
												.append("monthParam", new Document("$push", "$month"))),
								new Document("$unwind", new Document("path", "$detail")),
								new Document("$unwind", new Document("path", "$detail")),
								new Document("$group", new Document("_id", "$_id")
										.append("totalAmount", new Document("$sum", "$detail.totalPrice"))
										.append("totalBookCount", new Document("$sum", "$detail.bookOrderCount"))
										.append("totalOrderCount", new Document("$sum", 1L))
										.append("monthParam", new Document("$push", "$monthParam"))),
								new Document("$project",
										new Document("monthParam",
												new Document("$arrayElemAt", Arrays.asList("$monthParam", 0L)))
														.append("totalAmount", "$totalAmount")
														.append("totalBookCount", "$totalBookCount")
														.append("totalOrderCount", "$totalOrderCount")),
								new Document("$project",
										new Document("month",
												new Document("$arrayElemAt", Arrays.asList("$monthParam", 0L)))
														.append("totalAmount", "$totalAmount")
														.append("totalBookCount", "$totalBookCount")
														.append("totalOrderCount", "$totalOrderCount"))));
		ArrayList<Document> statistics = new ArrayList<>(2);
	    for (Document document : result) {
	    	statistics.add(document);
	    }

	    return statistics;
	}

}
