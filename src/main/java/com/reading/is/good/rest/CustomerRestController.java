package com.reading.is.good.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reading.is.good.dto.CustomerDTO;
import com.reading.is.good.pojo.Customer;
import com.reading.is.good.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO){
		if(customerService.findByEmail(customerDTO.getEmail()) != null)
			return new ResponseEntity<>("Already Exists", HttpStatus.CONFLICT);
		else {
			customerService.save(customerDTO);
			return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
		}
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<Page<Customer>> getAllCustomer(Pageable pageable){
		return new ResponseEntity<>(customerService.findAll(pageable), HttpStatus.OK);
	}
}