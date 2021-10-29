package com.reading.is.good.getir.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.reading.is.good.dto.CustomerDTO;
import com.reading.is.good.pojo.Customer;
import com.reading.is.good.service.CustomerService;

@SpringBootTest
public class CustomerServiceTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CustomerService customerService;

	@BeforeEach
	void init() {
		mongoTemplate.dropCollection(Customer.class);
	}

	private CustomerDTO createCustomerDT(String address, String name, String email, String lastName, String phone) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setAddress(address);
		customerDTO.setName(name);
		customerDTO.setLastName(lastName);
		customerDTO.setEmail(email);
		customerDTO.setPhone(phone);

		return customerDTO;
	}
	
	@Test
	public void testSave() throws Exception{
		CustomerDTO customerDTO = createCustomerDT("Istanbul", "John", "johnblack@mail.com", "Black", "+90112233");		
		customerService.save(customerDTO);
		
		Customer customer = customerService.findByEmail(customerDTO.getEmail());
		
		assertNotNull(customer);
		assertEquals(customerDTO.getEmail(), customer.getEmail());
	}
	
	@AfterEach
	void tearDown() {
		mongoTemplate.dropCollection(Customer.class);
	}

}
