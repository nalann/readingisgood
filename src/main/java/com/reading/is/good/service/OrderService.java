package com.reading.is.good.service;

import java.util.List;
import java.util.Optional;

import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.pojo.Order;

public interface OrderService {
	Order save(OrderDTO orderDTO);
	
	List<Order> findByEmail(String email);
	
	Optional<Order> findById(String id);
	
	List<Order> findByDateInterval(long startDate, long endDate);
}
