package com.reading.is.good.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = " Stock model documentation", description = "Stock Model")
public class OrderUpdateDTO {

	@ApiModelProperty(value = "Updated order count")
	private int count;

	@ApiModelProperty(value = "Order ID")
	private String orderId;
	
	@ApiModelProperty(value = "Updated book title")
	private String bookName;
	
	@ApiModelProperty(value = "Updated book author")
	private String author;
	
	@ApiModelProperty(value = "Updated order status")
	private String orderStatus;
	
	private double totalPrice;

	public OrderUpdateDTO() {

	}

	public OrderUpdateDTO(int count, String orderId, String bookName, String author, double totalPrice) {
		this.count = count;
		this.orderId = orderId;
		this.bookName = bookName;
		this.author = author;
		this.totalPrice = totalPrice;
	}
	
	public OrderUpdateDTO(String orderId, String orderStatus) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
