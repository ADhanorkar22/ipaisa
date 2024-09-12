package com.Ipaisa.dto;

public class Payout {

	private String apiId;
	private String bankId;
	private String acctNumber;
	private String beneAcctNumber;
	private String amount;
	private String purpose;
	public Payout(String apiId, String bankId, String acctNumber, String beneAcctNumber, String amount,
			String purpose) {
		super();
		this.apiId = apiId;
		this.bankId = bankId;
		this.acctNumber = acctNumber;
		this.beneAcctNumber = beneAcctNumber;
		this.amount = amount;
		this.purpose = purpose;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAcctNumber() {
		return acctNumber;
	}
	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}
	public String getBeneAcctNumber() {
		return beneAcctNumber;
	}
	public void setBeneAcctNumber(String beneAcctNumber) {
		this.beneAcctNumber = beneAcctNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Override
	public String toString() {
		return "Payout [apiId=" + apiId + ", bankId=" + bankId + ", acctNumber=" + acctNumber + ", beneAcctNumber="
				+ beneAcctNumber + ", amount=" + amount + ", purpose=" + purpose + "]";
	}
	
	
	
}
