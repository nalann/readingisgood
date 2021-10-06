package com.reading.is.good.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reading.is.good.dto.CustomerDTO;
import com.reading.is.good.pojo.Customer;

public interface CustomerService {
	Customer save(CustomerDTO customerDTO);
	
	Page<Customer> findAll(Pageable pageable);
	
	Customer findByEmail(String email);
}
