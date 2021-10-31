package com.reading.is.good.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reading.is.good.common.Utils;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.dto.OrderUpdateDTO;
import com.reading.is.good.exception.StockCannotUpdatedException;
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

	@Transactional
	@Override
	public Order save(OrderDTO orderDTO) {
		return orderRepositor.save(createOrderObj(orderDTO));
	}

	@Transactional
	@Override
	public Page<Order> findByEmail(String email, Pageable pageable) {
		return orderRepositor.findByEmail(email, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
	}

	// Converting OrderDTO object to Order object
	private Order createOrderObj(OrderDTO orderDTO) {
		Order order = new Order();
		order.setAddress(orderDTO.getAddress());
		order.setCustomerPhone(orderDTO.getCustomerPhone());
		order.setEmail(orderDTO.getEmail());
		order.setOrderDate(Utils.convertStrToDateLong(orderDTO.getOrderDate()));
		order.setDetail(new ArrayList<Detail>());
		order.setOrderStatus(orderDTO.getOrderStatus());

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

	@Transactional
	@Override
	public List<Order> findByDateInterval(long startDate, long endDate) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("orderDate").gte(startDate), Criteria.where("orderDate").lt(endDate));
		Query query = new Query().addCriteria(criteria);
		List<Order> orders = mongoTemplate.find(query, Order.class);

		return orders;
	}

	@Transactional
	@Override
	public void orderStockUpdate(OrderUpdateDTO orderUpdateDTO) {
		//This function is using for updating order details. bookOrderCount and totalPrice should be updated with new one.
		Query query = new Query(Criteria.where("id").is(orderUpdateDTO.getOrderId()));
		Order order = mongoTemplate.findOne(query, Order.class);

		order.getDetail().forEach(detail -> {
			if (orderUpdateDTO.getBookName().equals(detail.getBookName())
					&& orderUpdateDTO.getAuthor().equals(detail.getAuthor())) {
				detail.setBookOrderCount(orderUpdateDTO.getCount());
				detail.setTotalPrice(orderUpdateDTO.getTotalPrice());
			} else {
				throw new StockCannotUpdatedException("Stock is not enough for: " + detail.getBookName());
			}

		});

		mongoTemplate.save(order);
	}

	@Transactional
	@Override
	public void updateOrder(OrderUpdateDTO orderUpdate) {
		Query query = new Query(Criteria.where("id").is(orderUpdate.getOrderId()));
		Update update = new Update().set("orderStatus", orderUpdate.getOrderStatus());
		mongoTemplate.updateFirst(query, update, Order.class);
	}
}
