package com.Ipaisa.dto;

import java.util.Map;

public class DcryptResponse {

	private String status;
	private String message;
	private Map<String, String> data;

	public DcryptResponse() {
		// TODO Auto-generated constructor stub
	}

	public DcryptResponse(String status, String message, Map<String, String> data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public DcryptResponse(String string, String string2, String decryptedPayload) {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	
	
	
	

}
