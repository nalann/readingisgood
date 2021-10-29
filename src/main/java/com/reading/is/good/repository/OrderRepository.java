package com.reading.is.good.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reading.is.good.pojo.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	Page<Order> findByEmail(String email, Pageable pageable);
	
	Optional<Order> findById(String id);
}
