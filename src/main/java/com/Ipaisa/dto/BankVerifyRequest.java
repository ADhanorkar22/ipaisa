package com.Ipaisa.dto;

public class BankVerifyRequest {
	 private String accountNumber;
	    private String bankIfsc;
	   
	    public BankVerifyRequest() {
			// TODO Auto-generated constructor stub
		}
		public BankVerifyRequest(String accountNumber, String bankIfsc) {
			super();
			this.accountNumber = accountNumber;
			this.bankIfsc = bankIfsc;
			
		}
		public String getAccountNumber() {
			return accountNumber;
		}
		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}
		public String getBankIfsc() {
			return bankIfsc;
		}
		public void setBankIfsc(String bankIfsc) {
			this.bankIfsc = bankIfsc;
		}
		

	    
	    
		


		@Override
		public String toString() {
			return "BankVerifyRequest [accountNumber=" + accountNumber + ", bankIfsc=" + bankIfsc + "]";
		}
		}
		
