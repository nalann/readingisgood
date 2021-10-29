package com.reading.is.good.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Customer Api model documentation", description = "Customer Model")
public class CustomerDTO {

	@ApiModelProperty(value = "Customer Name")
	private String name;
	
	@ApiModelProperty(value = "Customer Last Name")
	private String lastName;
	
	@ApiModelProperty(value = "Customer Email. It should be unique")
	private String email;
	
	@ApiModelProperty(value = "Customer Address")
	private String address;
	
	@ApiModelProperty(value = "Customer Phone")
	private String phone;
	
	public CustomerDTO(){}
	
	public CustomerDTO(String name, String lastName, String email, String address, String phone) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phone = phone;		 
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
