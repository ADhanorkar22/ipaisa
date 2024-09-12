package com.Ipaisa.CustomExceptions;

public class KeyResponse {

	private String Key;
	private boolean Status;
	
	public KeyResponse() {
		// TODO Auto-generated constructor stub
	}

	public KeyResponse(String key, boolean status) {
		super();
		Key = key;
		Status = status;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}
	
	
	
}
