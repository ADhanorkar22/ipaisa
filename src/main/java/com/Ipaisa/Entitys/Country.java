package com.Ipaisa.Entitys;

import java.util.List;

public class Country {

	private String name;
	private String isoCode;
	private String flag;
	private String phonecode;
	private String currency;
	private String latitude;
	private String longitude;
	 private List<Timezones> timezones;
	
	public Country() {
		
	}


	public Country(String name, String isoCode, String flag, String phonecode, String currency, String latitude,
			String longitude, List<Timezones> timezones) {
		super();
		this.name = name;
		this.isoCode = isoCode;
		this.flag = flag;
		this.phonecode = phonecode;
		this.currency = currency;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timezones = timezones;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIsoCode() {
		return isoCode;
	}


	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getPhonecode() {
		return phonecode;
	}


	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public List<Timezones> getTimezone() {
		return timezones;
	}


	public void setTimezone(List<Timezones> timezone) {
		this.timezones = timezone;
	}
	
	
	

	

}
