package com.reading.is.good.dto;

import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = " Order Api model documentation", description = "Order Model")
public class OrderDTO {

	@ApiModelProperty(value = "Detail of Order")
	private ArrayList<DetailDTO> detail;

	@ApiModelProperty(value = "Customer email")
	private String email;

	@ApiModelProperty(value = "Order Creation Date")
	private String orderDate;

	@ApiModelProperty(value = "Customer Address")
	private String address;

	@ApiModelProperty(value = "Customer Phone")
	private String customerPhone;

	@ApiModelProperty(value = "Order Status. It should be shopping , approved (means payment done), canceled ")
	private String orderStatus;

	public OrderDTO() {
	}

	public OrderDTO(String email, ArrayList<DetailDTO> detail, String orderDate, String address, String customerPhone,
			String orderStatus) {
		this.email = email;
		this.detail = detail;
		this.orderDate = orderDate;
		this.address = address;
		this.customerPhone = customerPhone;
		this.orderStatus = orderStatus;
	}

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
