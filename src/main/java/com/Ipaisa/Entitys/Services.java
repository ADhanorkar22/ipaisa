package com.Ipaisa.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Services {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name="transation_slab")
	private String transactionSlab;
	
	
	public Services() {
		// TODO Auto-generated constructor stub
	}


	public Services(Long id, String transactionType, String transactionSlab) {
		super();
		this.id = id;
		this.transactionType = transactionType;
		this.transactionSlab = transactionSlab;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public String getTransactionSlab() {
		return transactionSlab;
	}


	public void setTransactionSlab(String transactionSlab) {
		this.transactionSlab = transactionSlab;
	}


	@Override
	public String toString() {
		return "Services [id=" + id + ", transactionType=" + transactionType + ", transactionSlab=" + transactionSlab
				+ "]";
	}
	
   

}
