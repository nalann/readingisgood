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
import com.reading.is.good.exception.StockCannotUpdatedException;
import com.reading.is.good.pojo.Book;
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

		Book existingBook = findByBookNameAndAuthor(bookDTO.getBookName(), bookDTO.getAuthor());

		if (existingBook != null) {
			existingBook.setStock(existingBook.getStock() + 1);
			return bookRepository.save(existingBook);
		} else {
			Book book = new Book();
			book.setAuthor(bookDTO.getAuthor());
			book.setBookName(bookDTO.getBookName());
			book.setCategory(bookDTO.getCategory());
			book.setPrice(bookDTO.getPrice());
			book.setPublisher(bookDTO.getPublisher());
			book.setStock(1);
			return bookRepository.save(book);
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

	@Override
	public void updateBookStock(OrderDTO orderDTO, boolean isDecrease) {
		orderDTO.getDetail().forEach(detail -> {
			Query query = new Query(Criteria.where("bookName").is(detail.getBookName()))
					.addCriteria(Criteria.where("stock").gte(detail.getBookOrderCount()));
			Book book = mongoTemplate.findOne(query, Book.class);

			if (book != null) {
				Update update;
				if(isDecrease)
					update = new Update().inc("stock", -detail.getBookOrderCount());
				else
					update = new Update().inc("stock", detail.getBookOrderCount());
				mongoTemplate.updateFirst(query, update, Book.class);
			} else {
				throw new StockCannotUpdatedException("Stock is not enough for: " + detail.getBookName());
			}
		});
	}

}
