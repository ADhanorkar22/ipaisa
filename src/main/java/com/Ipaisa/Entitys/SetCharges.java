package com.Ipaisa.Entitys;

public class SetCharges {
	
	private String provider;
	private String transactionType;
	private String transactionSlab;
	private String amount;
	
	public SetCharges() {
		// TODO Auto-generated constructor stub
	}

	public SetCharges(String provider, String transactionType, String transactionSlab, String amount) {
		super();
		this.provider = provider;
		this.transactionType = transactionType;
		this.transactionSlab = transactionSlab;
		this.amount = amount;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionSlab() {
		return transactionSlab;
	}

	public void setTransactionSlab(String transactionSlab) {
		this.transactionSlab = transactionSlab;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SetCharges [provider=" + provider + ", transactionType=" + transactionType + ", transactionSlab="
				+ transactionSlab + ", amount=" + amount + "]";
	}

	

}
