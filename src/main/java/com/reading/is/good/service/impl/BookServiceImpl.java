package com.reading.is.good.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reading.is.good.dto.BookDTO;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.dto.OrderUpdateDTO;
import com.reading.is.good.exception.StockCannotUpdatedException;
import com.reading.is.good.pojo.Book;
import com.reading.is.good.pojo.Order;
import com.reading.is.good.repository.BookRepository;
import com.reading.is.good.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Transactional
	@Override
	public Book saveOrUpdate(BookDTO bookDTO) {

		// getting existing book information to update current book.
		Book existingBook = findByBookNameAndAuthor(bookDTO.getBookName(), bookDTO.getAuthor());

		// if stock comes 0 from rest service, using directly 1 to update stock
		// information
		if (bookDTO.getStock() == 0) {
			if (existingBook != null) {
				existingBook.setStock(existingBook.getStock() + 1);
				return bookRepository.save(existingBook);
			} else {
				Book book = new Book(bookDTO.getBookName(), bookDTO.getAuthor(), bookDTO.getPublisher(),
						bookDTO.getCategory(), bookDTO.getPrice(), 1);
				return bookRepository.save(book);
			}
		}
		// if stock does not come 0 from rest service, using directly existing stock
		// information to update stock
		else {
			if (existingBook != null) {
				existingBook.setStock(existingBook.getStock() + bookDTO.getStock());
				return bookRepository.save(existingBook);
			} else {
				Book book = new Book(bookDTO.getBookName(), bookDTO.getAuthor(), bookDTO.getPublisher(),
						bookDTO.getCategory(), bookDTO.getPrice(), bookDTO.getStock());
				return bookRepository.save(book);
			}
		}
	}

	@Transactional
	@Override
	public Book findByBookName(String bookName) {
		return bookRepository.findByBookName(bookName);
	}

	@Transactional
	@Override
	public Book findByBookNameAndAuthor(String bookName, String author) {
		return bookRepository.findByBookNameAndAuthor(bookName, author);
	}

	@Transactional
	@Override
	public void updateBookStock(OrderDTO orderDTO, boolean isDecrease) {
		// This function is using for updating book stock. When the order stock is
		// changed, the book stock should be changed based on the current stock info
		// isDecrease is false when the error occurs during order save operation.
		// Otherwise it is true.
		orderDTO.getDetail().forEach(detail -> {
			Query query = new Query(Criteria.where("bookName").is(detail.getBookName()))
					.addCriteria(Criteria.where("stock").gte(detail.getBookOrderCount()));
			Book book = mongoTemplate.findOne(query, Book.class);
			if (book != null) {
				Update update;
				if (isDecrease)
					update = new Update().inc("stock", -detail.getBookOrderCount());
				else
					update = new Update().inc("stock", detail.getBookOrderCount());
				mongoTemplate.updateFirst(query, update, Book.class);
			} else {
				throw new StockCannotUpdatedException("Stock is not enough for: " + detail.getBookName());
			}
		});
	}

	@Transactional
	@Override
	public void bookStockUpdate(OrderUpdateDTO orderUpdateDTO) {
		// This function is using when the use change the book order count.
		// Firstly getting old order count and extract the new order count. After that
		// update the stock count by using this operation result. For example: result = oldOrderCount - newOrderCount.
		Query query = new Query(Criteria.where("id").is(orderUpdateDTO.getOrderId()));
		Order order = mongoTemplate.findOne(query, Order.class);

		order.getDetail().forEach(detail -> {
			if (orderUpdateDTO.getBookName().equals(detail.getBookName())
					&& orderUpdateDTO.getAuthor().equals(detail.getAuthor())) {
				Query bookQuery = new Query(Criteria.where("bookName").is(detail.getBookName()))
						.addCriteria(Criteria.where("stock").gte(detail.getBookOrderCount()));
				Book book = mongoTemplate.findOne(bookQuery, Book.class);
				if (book != null) {

					int stockChangingCount = detail.getBookOrderCount() - orderUpdateDTO.getCount();
					detail.setBookOrderCount(orderUpdateDTO.getCount());
					Update update = new Update().inc("stock", stockChangingCount);
					mongoTemplate.updateFirst(bookQuery, update, Book.class);
				} else {
					throw new StockCannotUpdatedException("Stock is not enough for: " + detail.getBookName());
				}
			}
		});
	}

	@Transactional
	@Override
	public void cancelOrder(Order order) {
		//This function is using for updating book stock information when the order is canceled.
		order.getDetail().forEach(detail -> {
			Query query = new Query(Criteria.where("bookName").is(detail.getBookName()))
					.addCriteria(Criteria.where("stock").gte(detail.getBookOrderCount()));
			Update update = new Update().inc("stock", detail.getBookOrderCount());
			mongoTemplate.updateFirst(query, update, Book.class);
		});
	}
}
