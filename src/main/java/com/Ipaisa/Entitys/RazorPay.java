package com.Ipaisa.Entitys;

public class RazorPay {
	
	  private String account_number;
      private String fund_account_id;
      private Double amount;
      private String currency;
      private String mode;
      private String purpose;
      private String queue_if_low_balance;
      private String reference_id;
      private String narration;
      private String note_key;
      private String NoteValue;
      
      public RazorPay() {
		// TODO Auto-generated constructor stub
	}

	public RazorPay(String account_number, String fund_account_id, Double amount, String currency, String mode,
			String purpose, String queue_if_low_balance, String reference_id, String narration, String note_key,
			String noteValue) {
		super();
		this.account_number = account_number;
		this.fund_account_id = fund_account_id;
		this.amount = amount;
		this.currency = currency;
		this.mode = mode;
		this.purpose = purpose;
		this.queue_if_low_balance = queue_if_low_balance;
		this.reference_id = reference_id;
		this.narration = narration;
		this.note_key = note_key;
		NoteValue = noteValue;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getFund_account_id() {
		return fund_account_id;
	}

	public void setFund_account_id(String fund_account_id) {
		this.fund_account_id = fund_account_id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getQueue_if_low_balance() {
		return queue_if_low_balance;
	}

	public void setQueue_if_low_balance(String queue_if_low_balance) {
		this.queue_if_low_balance = queue_if_low_balance;
	}

	public String getReference_id() {
		return reference_id;
	}

	public void setReference_id(String reference_id) {
		this.reference_id = reference_id;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getNote_key() {
		return note_key;
	}

	public void setNote_key(String note_key) {
		this.note_key = note_key;
	}

	public String getNoteValue() {
		return NoteValue;
	}

	public void setNoteValue(String noteValue) {
		NoteValue = noteValue;
	}
      
      

}
