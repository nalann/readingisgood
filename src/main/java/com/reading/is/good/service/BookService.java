package com.reading.is.good.service;

import java.util.List;

import com.reading.is.good.dto.BookDTO;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.pojo.Book;

public interface BookService {

	Book saveOrUpdate(BookDTO bookDTO);
	
	List<Book> findAll();
	
	Book findByBookName(String bookName);
	
	Book findByBookNameAndAuthor(String bookName, String author);
	
	boolean updateBookStock(OrderDTO orderDTO);
}
