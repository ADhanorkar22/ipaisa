package com.Ipaisa.Entitys;

public class City {
	
      private String name;
      private String countryCode;
      private String stateCode;
      private String latitude;
     private String longitude;
	public City(String name, String countryCode, String stateCode, String latitude, String longitude) {
		super();
		this.name = name;
		this.countryCode = countryCode;
		this.stateCode = stateCode;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
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
