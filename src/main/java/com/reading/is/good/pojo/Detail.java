package com.reading.is.good.pojo;

public class Detail {
	private int bookOrderCount;
	private String bookName;
	private String author;
	private double totalPrice;
	
	public Detail() {}
	
	public Detail(int bookOrderCount, String bookName, String author, double totalPrice) {
		this.bookOrderCount = bookOrderCount;
		this.bookName = bookName;
		this.author = author;
		this.totalPrice = totalPrice;
	}

	public int getBookOrderCount() {
		return bookOrderCount;
	}

	public void setBookOrderCount(int bookOrderCount) {
		this.bookOrderCount = bookOrderCount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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

}
