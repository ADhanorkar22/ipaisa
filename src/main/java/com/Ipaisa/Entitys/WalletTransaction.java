package com.Ipaisa.Entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GeneratorType;

import com.Ipaisa.dto.TransactionIdGenerator;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WTransaction")
public class WalletTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Integer id;

	@Column(name = "transaction_uuid", unique = true, nullable = false)
	private String transactionId;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User senderid;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private User receiverid;

	@Column(name = "transfer_amoount")
	private double amount;

	@Column(name = "remark")
	private String remark;

	@Column(name = "transaction_date")
	private LocalDateTime date;

	@Enumerated(EnumType.STRING)
	@Column(name = "u_type")
	private Utype utype;

	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionType transactionType;

	public WalletTransaction(User senderid, User receiverid, double amount, String remark,
			TransactionType transactionType) {
		super();
		// this.id = id;
		 this.transactionId = TransactionIdGenerator.generateTransactionId();
		this.senderid = senderid;
		this.receiverid = receiverid;
		this.amount = amount;
		this.remark = remark;
		this.date = LocalDateTime.now();
		this.utype = utype;
		this.transactionType = transactionType;
	}

	public WalletTransaction() {
		 this.transactionId = TransactionIdGenerator.generateTransactionId();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getSenderid() {
		return senderid;
	}

	public void setSenderid(User senderid) {
		this.senderid = senderid;
	}

	public User getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(User receiverid) {
		this.receiverid = receiverid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Utype getUtype() {
		return utype;
	}

	public void setUtype(Utype utype) {
		this.utype = utype;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	

}
