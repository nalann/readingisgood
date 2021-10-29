package com.reading.is.good.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
	@Id
	private String id;
	private String name;
	private String lastName;
	@Indexed(unique=true)
	private String email;
	private String address;
	private String phone;
	
	public Customer(){}
	
	public Customer(String name, String lastName, String email, String address, String phone) {
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

	public String getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
