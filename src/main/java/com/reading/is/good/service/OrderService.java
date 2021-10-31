package com.reading.is.good.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.dto.OrderUpdateDTO;
import com.reading.is.good.pojo.Order;

public interface OrderService {
	Order save(OrderDTO orderDTO);
	
	Page<Order> findByEmail(String email, Pageable pageable);
	
	Optional<Order> findById(String id);
	
	List<Order> findByDateInterval(long startDate, long endDate);
	
	void orderStockUpdate(OrderUpdateDTO orderUpdateDTO);
	
	void updateOrder(OrderUpdateDTO orderUpdate);
}
