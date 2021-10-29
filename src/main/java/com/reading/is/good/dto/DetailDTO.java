package com.reading.is.good.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Detail of Order Api model documentation", description = "Detail of Order Model")
public class DetailDTO {
	
	@ApiModelProperty(value = "Total Book Order Count")
	private int bookOrderCount;
	
	@ApiModelProperty(value = "Ordered Book Name")
	private String bookName;
	
	@ApiModelProperty(value = "Ordered Book Author")
	private String author;
	
	@ApiModelProperty(value = "Total Price of Book Order")
	private double totalPrice;
	
    public DetailDTO() {}
	
	public DetailDTO(int bookOrderCount, String bookName, String author, double totalPrice) {
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
