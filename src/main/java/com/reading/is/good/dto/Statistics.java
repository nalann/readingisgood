package com.reading.is.good.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = " Statistics Api model documentation", description = "Statistics Model")
public class Statistics {
	
	@ApiModelProperty(value = "Statistics of Total Order Count")
	private int totalOrderCount;
	
	@ApiModelProperty(value = "Statistics of Total Order Amount")
	private double totalAmount;
	
	@ApiModelProperty(value = "Statistics of Total Ordered Book Count")
	private int totalBookCount;
	
	@ApiModelProperty(value = "Month information")
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
