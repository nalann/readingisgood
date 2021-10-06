package com.reading.is.good.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reading.is.good.pojo.Book;

public interface BookRepository extends MongoRepository<Book, String> {
	
	Book findByBookName(String bookName);
	
	Book findByBookNameAndAuthor(String bookName, String author);

}
