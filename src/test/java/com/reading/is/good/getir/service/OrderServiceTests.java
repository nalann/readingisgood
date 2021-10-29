package com.reading.is.good.getir.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.reading.is.good.dto.DetailDTO;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.repository.OrderRepository;
import com.reading.is.good.service.OrderService;

@SpringBootTest
public class OrderServiceTests {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@BeforeEach
	void init() {
		mongoTemplate.dropCollection(Order.class);
	}
	
	private OrderDTO createOrderDTO() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setEmail("test@test.com");
		orderDTO.setOrderDate("29-10-2021 14:02:05");
		orderDTO.setAddress("Istanbul");
		orderDTO.setCustomerPhone("+900002");
		orderDTO.setDetail(new ArrayList<DetailDTO>());
		DetailDTO detailDTO = new DetailDTO();
		detailDTO.setAuthor("Victor Hugo");
		detailDTO.setBookName("Bir Idam Mahkumunun Son Gunu");
		detailDTO.setBookOrderCount(1);
		detailDTO.setTotalPrice(6.0);
		orderDTO.getDetail().add(detailDTO);
		
		return orderDTO;
	}
	
	@Test
	public void testSave() throws Exception{
		OrderDTO orderDTO = createOrderDTO();
		orderService.save(orderDTO);
		
		List<Order> order = orderRepository.findAll();
		
		assertEquals(1, order.size());
	}
	
	@AfterEach
	void tearDown() {
		mongoTemplate.dropCollection(Order.class);
	}

}
