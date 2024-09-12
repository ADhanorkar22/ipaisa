package com.Ipaisa.Entitys;

public class BankDTO {
	
	private String name;
	private String bankName;
	private String branch;
	private String accountNumber;
	private String ifsc;
	
	public BankDTO() {
		
	}
	public BankDTO(String name, String bankName, String branch, String ifsc) {
		super();
		this.name = name;
		this.bankName = bankName;
		this.branch = branch;
		this.ifsc = ifsc;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	@Override
	public String toString() {
		return "BankDTO [name=" + name + ", bankName=" + bankName + ", branch=" + branch + ", accountNumber="
				+ accountNumber + ", ifsc=" + ifsc + "]";
	}
	
	
	
}
