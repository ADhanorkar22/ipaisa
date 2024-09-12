package com.Ipaisa.Entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "user_address")
public class UsersAddress {

	@Id
	private String id;
	@Column(name = "country")
	private String country;
	@Column(name = "state")
	private String state;
	@Column(name = "local_address")
	private String address;
	@Column(name = "pincode")
	private int pincode;
	@Column(name="city")
	private String city;
	@OneToOne
	@JoinColumn(name = "u_id")
	@MapsId
	private User user;

	public UsersAddress() {
		
	}

	public UsersAddress(String id, String country, String state, String address, int pincode,User user) {
		super();
		this.id = id;
		this.country = country;
		this.state = state;
		this.address = address;
		this.pincode = pincode;
		this.user = user;
	}
	public UsersAddress(String country, String state, String address, int pincode,String city) {
		super();
		this.country = country;
		this.state = state;
		this.address = address;
		this.pincode = pincode;
		this.city=city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
