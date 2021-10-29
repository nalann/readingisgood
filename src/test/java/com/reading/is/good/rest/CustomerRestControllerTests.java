package com.reading.is.good.rest;

import java.nio.charset.StandardCharsets;
import java.util.List;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reading.is.good.dto.CustomerDTO;
import com.reading.is.good.pojo.Customer;
import com.reading.is.good.service.CustomerService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class CustomerRestControllerTests {
	@MockBean
	private CustomerService customerService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	private CustomerDTO customerDTO;

	private Customer customer;

	private List<Customer> customers;

	private Page<Customer> pageCustomer;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		customerDTO = new CustomerDTO("John", "Snow", "johnsnow@nothing.com", "London", "5265");

		customer = new Customer("John", "Snow", "johnsnow@nothing.com", "London", "5265");

		customers = List.of(new Customer("John", "Snow", "johnsnow@nothing.com", "London", "5265"),
				new Customer("Sahra", "Ay", "sahraay@mail.com", "Istanbul", "478"),
				new Customer("Ali", "Cam", "alican@mail.com", "Ankara", "365"));

		pageCustomer = new PageImpl<Customer>(customers);
	}

	@Test
	void testCustomerRestSave() throws Exception {
		Mockito.when(customerService.save(Mockito.any(CustomerDTO.class))).thenReturn(customer);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/customers/save").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customerDTO).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		Assertions.assertThat(result).isNotNull();
		String customerJson = result.getResponse().getContentAsString();
		Assertions.assertThat(customerJson).isNotEmpty();
		Assertions.assertThat(customerJson).isNotEqualToIgnoringCase(objectMapper.writeValueAsString(customerDTO));
	}

	@Test
	void testCustomerRestExistingCustomer() throws Exception {
		Mockito.when(customerService.findByEmail(customer.getEmail())).thenReturn(customer);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/customers/save").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customerDTO).getBytes(StandardCharsets.UTF_8))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();

		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(409);
	}

	@Test
	void testCustomerRestGetAllCustomer() throws Exception {
		Pageable pageable = PageRequest.of(0, 1);
		Mockito.when(customerService.findAll(pageable)).thenReturn(pageCustomer);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers/all?page=0&size=1").accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String customerJson = result.getResponse().getContentAsString();
		
		Assertions.assertThat(customerJson).isNotEmpty();
		Assertions.assertThat(customerJson).isNotEqualToIgnoringCase(objectMapper.writeValueAsString(customers.get(0)));
		Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}
}
