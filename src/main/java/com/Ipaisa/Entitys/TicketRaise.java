package com.Ipaisa.Entitys;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Support_Ticket")
public class TicketRaise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ticket_id")
	private Long id;
	@Column(name = "remark")
	private String remark;
	@Column(name = "issue_Type")
	private String issueType;
	@Column(name = "subject")
	private String subject;
	@Column(name = "Bank_Accounts")
	private String bankAccount;
	@Column(name = "payment_Mode")
	private String paymentMode;
	@Column(name = "UTR_REF_Number")
	private Long utr_ref_Number;
	
	@Column(name = "receipt_Path")
	private String receiptPath;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TStatus status;
	@Column(name = "date")
	private LocalDate date;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "parent_id")
	private String parent;
	@Column(name="transfer_amount")
	private Double amount;
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="parentName")
	private String parentName;

	public TicketRaise() {

	}
	
	

	public TicketRaise(String remark, String issueType, String subject,
			User user, String parent) {
		super();
		this.remark = remark;
		this.issueType = issueType;
		this.subject = subject;
		this.status = TStatus.PENDING;
		this.date = LocalDate.now();
		this.user = user;
		this.parent = parent;
	}



	public TicketRaise( String remark, String issueType, String subject, String bankAccount, String paymentMode,
			Long utr_ref_Number, User user, String parent,Double amount,String createdBy,String parentName) {
		super();
		this.remark = remark;
		this.issueType = issueType;
		this.subject = subject;
		this.bankAccount = bankAccount;
		this.paymentMode = paymentMode;
		this.utr_ref_Number = utr_ref_Number;
//		this.receiptPath = receiptPath;
		this.status = status.PENDING;
		this.date = LocalDate.now();
		this.user = user;
		this.parent = parent;
		this.amount=amount;
		this.createdBy=createdBy;
		this.parentName=parentName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return utr_ref_Number;
	}

	public void setUtr_ref_Number(Long utr_ref_Number) {
		this.utr_ref_Number = utr_ref_Number;
	}

	public String getReceiptPath() {
		return receiptPath;
	}

	public void setReceiptPath(String receiptPath) {
		this.receiptPath = receiptPath;
	}

	public TStatus getStatus() {
		return status;
	}

	public void setStatus(TStatus status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
