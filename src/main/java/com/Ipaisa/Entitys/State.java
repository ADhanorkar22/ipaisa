package com.Ipaisa.Entitys;

public class State {
	
     private String name;
     private String isoCode;
    private String  countryCode;
     private String latitude;
     private String longitude;
     
     public State() {
		// TODO Auto-generated constructor stub
	}

	public State(String name, String isoCode, String countryCode, String latitude, String longitude) {
		super();
		this.name = name;
		this.isoCode = isoCode;
		this.countryCode = countryCode;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
     
	
  

}
