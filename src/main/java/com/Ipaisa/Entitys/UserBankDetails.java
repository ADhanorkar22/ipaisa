package com.Ipaisa.Entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "user_bank_details")
public class UserBankDetails {
	
	@Id
	private String id;
	
	@Column(name = "User_Name")
	private String name;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "branch")
	private String branch;
	
	@Column(name = "Acc_No")
	private Long accountNumber;
	
	@Column(name = "ifsc")
	private String ifsc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	

	public UserBankDetails() {

	}
	
	

	public UserBankDetails(String id, String name, String bankName, String branch, Long accountNumber, String ifsc,
			User user) {
		super();
		this.id = id;
		this.name = name;
		this.bankName = bankName;
		this.branch = branch;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
		this.user = user;
	}



	public UserBankDetails(String id, String bankName, String branch, Long accountNumber, String ifsc, User user) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.branch = branch;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
		this.user = user;
	}

	public UserBankDetails(String name, String bankName, String branch, Long accountNumber, String ifsc) {
		super();
		this.name = name;
		this.bankName = bankName;
		this.branch = branch;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
	}

	public UserBankDetails(String name, String bankName, String branch, String ifsc) {
		super();
		this.name = name;
		this.bankName = bankName;
		this.branch = branch;
		this.ifsc = ifsc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAcccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "UserBankDetails [id=" + id + ", name=" + name + ", bankName=" + bankName + ", branch=" + branch
				+ ", accountNumber=" + accountNumber + ", ifsc=" + ifsc + ", user=" + user + "]";
	}
	
 
}
