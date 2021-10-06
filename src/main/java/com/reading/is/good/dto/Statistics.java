package com.reading.is.good.dto;

public class Statistics {
	private int totalOrderCount;
	private double totalAmount;
	private int totalBookCount;
	private String month;

	public int getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(int totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalBookCount() {
		return totalBookCount;
	}

	public void setTotalBookCount(int totalBookCount) {
		this.totalBookCount = totalBookCount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
