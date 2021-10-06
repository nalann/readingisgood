package com.reading.is.good.dto;

public class DetailDTO {
	private int bookOrderCount;
	private String bookName;
	private String author;
	private double totalPrice;

	public int getBookOrderCount() {
		return bookOrderCount;
	}

	public void setBookOrderCount(int bookOrderCount) {
		this.bookOrderCount = bookOrderCount;
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
