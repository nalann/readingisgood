package com.reading.is.good.dto;

import java.util.ArrayList;

public class OrderDTO {

	private ArrayList<DetailDTO> detail;
	private String email;
	private String orderDate;
	private String address;
	private String customerPhone;

	public ArrayList<DetailDTO> getDetail() {
		return detail;
	}

	public void setDetail(ArrayList<DetailDTO> detail) {
		this.detail = detail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
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
