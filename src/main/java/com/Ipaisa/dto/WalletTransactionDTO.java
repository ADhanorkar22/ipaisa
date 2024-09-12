package com.Ipaisa.dto;

import java.time.LocalDateTime;

public class WalletTransactionDTO {
	private String senderName;
	private String senderId;
	private String TransactionType;
	private double amount;
	private String tranxid;
	private LocalDateTime date;
	private String remark;
	private String senderNumber;

	// Constructors, getters, and setters
	public WalletTransactionDTO(String senderName, String senderId, String senderTransactionType, double amount,
			LocalDateTime date, String remark, String senderNumber, String txtranxid) {
		this.senderName = senderName;
		this.senderId = senderId;
		this.TransactionType = senderTransactionType;
		this.amount = amount;
		this.date = date;
		this.remark = remark;
		this.senderNumber = senderNumber;
		this.tranxid = txtranxid;
	}

	// Getters and setters
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSenderNumber() {
		return senderNumber;
	}

	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}

	public String getTranxid() {
		return tranxid;
	}

	public void setTranxid(String tranxid) {
		this.tranxid = tranxid;
	}

}
