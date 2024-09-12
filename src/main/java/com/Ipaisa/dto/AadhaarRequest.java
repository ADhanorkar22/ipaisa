package com.Ipaisa.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AadhaarRequest {

    private String aadhaarNumber;


    // Constructors
    public AadhaarRequest() {
    }
    
    

    public AadhaarRequest(String aadhaarNumber) {
	super();
	this.aadhaarNumber = aadhaarNumber;
}



	// Getters and Setters
    @JsonProperty("aadhaarNumber")
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    @JsonProperty("aadhaarNumber")
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }



	@Override
	public String toString() {
		return "AadhaarRequest [aadhaarNumber=" + aadhaarNumber + "]";
	}

    
    
}