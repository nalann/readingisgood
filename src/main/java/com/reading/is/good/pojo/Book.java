package com.reading.is.good.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
@CompoundIndexes({
    @CompoundIndex(name = "book_author", def = "{'bookName' : 1, 'author': 1}", unique = true)
})
public class Book {

	@Id
	private String id;
	private String bookName;
	private String author;
	private String publisher;
	private int stock;
	private String category;
	private double price;
	
	public Book() {
	}

	public Book(String bookName, String author, String publisher, String category, double price) {
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.category = category;
		this.price = price;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

}
