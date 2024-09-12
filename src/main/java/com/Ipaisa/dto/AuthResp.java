package com.Ipaisa.dto;


public class AuthResp {
    private String message;
    private String token;
    private String userId;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String userType;
    private String bulkPayout;
    private String status;
    private String isFirstLogin;
    
  public AuthResp() {
	// TODO Auto-generated constructor stub
}

  public AuthResp(String message, String token, String userId, String mobileNumber, String firstName, String lastName,
			String userType, String bulkPayout,String status,String isFirstLogin) {
		super();
		this.message = message;
		this.token = token;
		this.userId = userId;
		this.mobileNumber = mobileNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userType = userType;
		this.bulkPayout = bulkPayout;
		this.status=status;
		this.isFirstLogin=isFirstLogin;
	}


public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getMobileNumber() {
	return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getUserType() {
	return userType;
}

public void setUserType(String userType) {
	this.userType = userType;
}

public String getBulkPayout() {
	return bulkPayout;
}

public void setBulkPayout(String bulkPayout) {
	this.bulkPayout = bulkPayout;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getIsFirstLogin() {
	return isFirstLogin;
}

public void setIsFirstLogin(String isFirstLogin) {
	this.isFirstLogin = isFirstLogin;
}
  


   
}