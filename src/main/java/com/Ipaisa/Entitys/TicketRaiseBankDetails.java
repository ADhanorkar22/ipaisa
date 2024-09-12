package com.Ipaisa.Entitys;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;


public class TicketRaiseBankDetails {
	
	private String remark;
	private String issueType;
	private String subject;
	private String bankAccount;
	private String paymentMode;
	private Long utrNumber;
	//private String receiptPath;

	//private String user;
	private Double amount;
//	private MultipartFile file;
	
	
	public TicketRaiseBankDetails() {
		
	}
	
	

	public TicketRaiseBankDetails(String remark, String issueType, String subject
			) {
		super();
		this.remark = remark;
		this.issueType = issueType;
		this.subject = subject;
		
	
		
		
	}



	public TicketRaiseBankDetails(String remark, String issueType, String subject, String bankAccount, String paymentMode,
			Long utrNumber, Double amount) {
		super();
		this.remark = remark;
		this.issueType = issueType;
		this.subject = subject;
		this.bankAccount = bankAccount;
		this.paymentMode = paymentMode;
		this.utrNumber = utrNumber;
	//	this.receiptPath = receiptPath;
		
	
		this.amount=amount;
		
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Long getUtr_ref_Number() {
		return utrNumber;
	}

	public void setUtr_ref_Number(Long utr_ref_Number) {
		this.utrNumber = utr_ref_Number;
	}

//	public String getReceiptPath() {
//		return receiptPath;
//	}
//
//	public void setReceiptPath(String receiptPath) {
//		this.receiptPath = receiptPath;
//	}


	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}





//
//	public MultipartFile getFile() {
//		return file;
//	}
//
//
//
//	public void setFile(MultipartFile file) {
//		this.file = file;
//	}



	@Override
	public String toString() {
		return "TicketRaiseBankDetails [remark=" + remark + ", issueType=" + issueType + ", subject=" + subject
				+ ", bankAccount=" + bankAccount + ", paymentMode=" + paymentMode + ", utr_ref_Number=" + utrNumber
				+ "]";
	}
	

	
	
	

}
