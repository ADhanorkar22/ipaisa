package com.Ipaisa.Entitys;

import lombok.*;

import java.time.LocalDate;


public class Transaction {


	private String receiverId;
	private String receiverName;
	private String receiverType;
	private double amount;
	private String remark;
	private LocalDate date;

	public Transaction() {
	}

	public Transaction(String receiverId, String receiverName, String receiverType, double amount, String remark) {
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.receiverType = receiverType;
		this.amount = amount;
		this.remark = remark;
		this.date = LocalDate.now();
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
