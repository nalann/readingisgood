package com.reading.is.good.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reading.is.good.common.Utils;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.exception.StockCannotUpdatedException;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.service.BookService;
import com.reading.is.good.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BookService bookService;
	
	@PostMapping(value = "/save")
	public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO){
		try {
			bookService.updateBookStock(orderDTO);
			orderService.save(orderDTO);
			return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
		}
		catch (StockCannotUpdatedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(value = "/get/by/id")
	public ResponseEntity<Order> getById(@RequestParam String id){
		Optional<Order> order = orderService.findById(id);
		if(order.isPresent()) {
			return new ResponseEntity<>(order.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping(value = "get/date/interval")
	public ResponseEntity<List<Order>> getByDateInterval(@RequestParam String startDate, @RequestParam String endDate){
		long startDateLong = Utils.convertStrToDateLong(startDate);
		long endDateLong = Utils.convertStrToDateLong(endDate);
		
		List<Order> orders = orderService.findByDateInterval(startDateLong, endDateLong);
		
		if(orders != null) {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 
	}

}
