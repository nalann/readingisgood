package com.reading.is.good.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reading.is.good.dto.CustomerDTO;
import com.reading.is.good.pojo.Customer;
import com.reading.is.good.repository.CustomerRepository;
import com.reading.is.good.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional
	@Override
	public Customer save(CustomerDTO customerDTO) {
		return customerRepository.save(convertCustomer(customerDTO));
	}

	@Transactional
	@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	@Transactional
	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
	}
	
	//Converting CustomerDTO object to Customer object
	private Customer convertCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setAddress(customerDTO.getAddress());
		customer.setEmail(customerDTO.getEmail());
		customer.setLastName(customerDTO.getLastName());
		customer.setName(customerDTO.getName());
		customer.setPhone(customerDTO.getPhone());
		
		return customer;
	}

}
