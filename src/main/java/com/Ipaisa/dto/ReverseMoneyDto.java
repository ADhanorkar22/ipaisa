package com.Ipaisa.dto;
//
//"receiverId": "RCP1713100084533",
//"receiverName": "Harsh",
//"receiverType": "Channel Partner",
//"amount": "100",
//"remark": "hii"
//
public class ReverseMoneyDto {
	private String senderId;
	private String receiverId;
	private String receiverName;
	private String userType;
	private Double amount;
	private String remark;
	public ReverseMoneyDto() {
		// TODO Auto-generated constructor stub
	}
	public ReverseMoneyDto(String senderId, String receiverId, String receiverName, String userType, Double amount,
			String remark) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.userType = userType;
		this.amount = amount;
		this.remark = remark;
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}











