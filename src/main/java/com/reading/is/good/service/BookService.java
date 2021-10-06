package com.reading.is.good.service;

import com.reading.is.good.dto.BookDTO;
import com.reading.is.good.dto.OrderDTO;
import com.reading.is.good.pojo.Book;

public interface BookService {

	Book saveOrUpdate(BookDTO bookDTO);
	
	Book findByBookName(String bookName);
	
	Book findByBookNameAndAuthor(String bookName, String author);
	
	void updateBookStock(OrderDTO orderDTO, boolean isDecrease);
}
