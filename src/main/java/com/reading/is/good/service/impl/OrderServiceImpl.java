package com.reading.is.good.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.reading.is.good.common.Utils;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.pojo.Detail;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.repository.OrderRepository;
import com.reading.is.good.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepositor;
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public Order save(OrderDTO orderDTO) {
		return orderRepositor.save(createOrderObj(orderDTO));
	}

	@Override
	public List<Order> findByEmail(String email) {
		// TODO Auto-generated method stub
		return orderRepositor.findByEmail(email);
	}
	
	private Order createOrderObj(OrderDTO orderDTO) {
		Order order = new Order();
		order.setAddress(orderDTO.getAddress());
		order.setCustomerPhone(orderDTO.getCustomerPhone());
		order.setEmail(orderDTO.getEmail());
		order.setOrderDate(Utils.convertStrToDateLong(orderDTO.getOrderDate()));
		order.setDetail(new ArrayList<Detail>());
		
		orderDTO.getDetail().stream().forEach(detail -> {
			Detail orderDetail = new Detail();
			orderDetail.setAuthor(detail.getAuthor());
			orderDetail.setBookName(detail.getBookName());
			orderDetail.setBookOrderCount(detail.getBookOrderCount());
			orderDetail.setTotalPrice(detail.getTotalPrice());
			order.getDetail().add(orderDetail);
		});
		return order;
	}

	@Override
	public Optional<Order> findById(String id) {
		return orderRepositor.findById(id);
	}

	@Override
	public List<Order> findByDateInterval(long startDate, long endDate) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("orderDate").gte(startDate), Criteria.where("orderDate").lt(endDate));
		Query query = new Query().addCriteria(criteria);
		List<Order> orders = mongoTemplate.find(query, Order.class);

		return orders;
	}
}
