package com.reading.is.good.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reading.is.good.pojo.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByEmail(String email);
	
	Optional<Order> findById(String id);
}
