package com.Ipaisa.Entitys;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
@Entity
@Table(name = "revMoneyTransaction")
public class ReverseMoney {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tId;
	@Column(name = "receiverId")
	private String receiverId;
	@Column(name = "reverseBy")
	private String reverseBy;
	@Column(name = "receiverName")
	private String receiverName;
	@Transient
	@Column(name = "userType")
	private String utype;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "date")
	private LocalDateTime dt;
	@Column(name = "remark")
	private String remark;
	@Transient
	@Column(name = "status")
	private String Status;
	public ReverseMoney() {
		// TODO Auto-generated constructor stub
	}
	public ReverseMoney(String receiverId, String receiverName,String revrseBy, String utype, Double amount, String remark) {
		super();
		// this.tId = tId;
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.reverseBy=revrseBy;
		this.utype = utype;
		this.amount = amount;
		this.dt = LocalDateTime.now();
		this.remark = remark;
//		Status = status;
	}
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
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
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDateTime getDt() {
		return dt;
	}
	public void setDt(LocalDateTime dt) {
		this.dt = dt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getReverseBy() {
		return reverseBy;
	}
	public void setReverseBy(String reverseBy) {
		this.reverseBy = reverseBy;
	}
	
}