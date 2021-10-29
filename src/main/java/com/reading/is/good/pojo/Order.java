package com.reading.is.good.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
public class Order {

	@Id
	private String id;
	private String email;
	private List<Detail> detail;
	private long orderDate;
	private String address;
	private String customerPhone;
	
	public Order() {}
	
	public Order(String email, List<Detail> detail, long orderDate, String address, String customerPhone) {
		this.email = email;
		this.detail = detail;
		this.orderDate = orderDate;
		this.address = address;
		this.customerPhone = customerPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Detail> getDetail() {
		return detail;
	}

	public void setDetail(List<Detail> detail) {
		this.detail = detail;
	}

	public String getId() {
		return id;
	}

	public long getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(long orderDate) {
		this.orderDate = orderDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

}
