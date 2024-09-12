package com.Ipaisa.DeepLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class KeyGenrateDto {



	private String phone;
	private double amount;
	private String email;
	private String furl;
	private String surl;
	private String txnid;
	private String firstname;
	private String productinfo;

	public KeyGenrateDto() {
		// TODO Auto-generated constructor stub
	}

	public KeyGenrateDto(String phone, double amount, String email, String furl, String surl, String txnid,
			String firstname, String productinfo) {
		super();
		this.phone = phone;
		this.amount = amount;
		this.email = email;
		this.furl = furl;
		this.surl = surl;
		this.txnid = txnid;
		this.firstname = firstname;
		this.productinfo = productinfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

}
