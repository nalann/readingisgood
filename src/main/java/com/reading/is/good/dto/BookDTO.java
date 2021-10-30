package com.reading.is.good.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Book Api model documentation", description = "Book Model")
public class BookDTO {

	@ApiModelProperty(value = "Book Name")
	private String bookName;

	@ApiModelProperty(value = "Author of Book")
	private String author;

	@ApiModelProperty(value = "Publisher of Book")
	private String publisher;

	@ApiModelProperty(value = "Stock of Book")
	private int stock;

	@ApiModelProperty(value = "Book Category")
	private String category;

	@ApiModelProperty(value = "Book Price")
	private double price;

	public BookDTO() {
	}

	public BookDTO(String bookName, String author, String publisher, String category, double price) {
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.category = category;
		this.price = price;
	}
	
	public BookDTO(String bookName, String author, String publisher, String category, double price, int stock) {
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.category = category;
		this.price = price;
		this.stock = stock;
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

}
