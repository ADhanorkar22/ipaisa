package com.Ipaisa.Entitys;

public class AddressDTO {
	private String country;
	private String state;
	private String city;
	private Integer pincode;
	private String address;
	
	public AddressDTO() {
		
	}
	public AddressDTO(String country, String state, String city, Integer pincode, String address) {
		super();
		this.country = country;
		this.state = state;
		this.city = city;
		this.pincode = pincode;
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	

}
