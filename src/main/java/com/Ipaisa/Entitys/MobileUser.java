//package com.Ipaisa.Entitys;
//
//import jakarta.annotation.Generated;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table
//public class MobileUser {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="user_id")
//	private Integer id;
//	@Column(name="mobile_number")
//	private String mobileNumber;
//	@Column(name="email")
//	private String email;
//	@Column(name="mpin")
//	private String mpin;
//	
//	 public MobileUser() {
//		// TODO Auto-generated constructor stub
//	}
//	public MobileUser(Integer id, String mobileno, String email, String mpin) {
//		super();
//		this.id = id;
//		this.mobileNumber = mobileno;
//		this.email = email;
//		this.mpin = mpin;
//	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public String getMobileno() {
//		return mobileNumber;
//	}
//	public void setMobileno(String mobileno) {
//		this.mobileNumber = mobileno;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getMpin() {
//		return mpin;
//	}
//	public void setMpin(String mpin) {
//		this.mpin = mpin;
//	}
//	
//	
//
//}
