package com.reading.is.good.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.reading.is.good.pojo.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	
	Customer findByEmail(String email);
	
	Page<Customer> findAll(Pageable pageable);

}
