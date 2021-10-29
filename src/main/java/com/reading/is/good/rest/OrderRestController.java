package com.reading.is.good.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.reading.is.good.exception.DateParseException;
import com.reading.is.good.exception.StockCannotUpdatedException;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.service.BookService;
import com.reading.is.good.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/orders")
@Api(value = "Order Api documentation")
public class OrderRestController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BookService bookService;
	
	@PostMapping(value = "/save")
	@ApiOperation(value = "New Order Addition Method")
	public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO){
		try {
			bookService.updateBookStock(orderDTO, true);
			orderService.save(orderDTO);
			return new ResponseEntity<>("Successfully Created", HttpStatus.CREATED);
		}
		catch (StockCannotUpdatedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		catch (DateParseException e) {
			bookService.updateBookStock(orderDTO, false);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/get/by/id")
	@ApiOperation(value = "Getting Order by order id")
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
	@ApiOperation(value = "Getting Order information based on time interval")
	public ResponseEntity<List<Order>> getByDateInterval(@RequestParam String startDate, @RequestParam String endDate){
		long startDateLong = Utils.convertStrToDateLong(startDate);
		long endDateLong = Utils.convertStrToDateLong(endDate);
		
		List<Order> orders = orderService.findByDateInterval(startDateLong, endDateLong);
		
		if(orders != null && !orders.isEmpty()) {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		 
	}
	
	@GetMapping(value = "get/all/orders/by/customer")
	@ApiOperation(value = "Getting Order information based on customer")
	public ResponseEntity<Page<Order>> getOrderByCustomer(@RequestParam String email, Pageable pageable){
		return new ResponseEntity<>(orderService.findByEmail(email, pageable), HttpStatus.OK);
	}

}
