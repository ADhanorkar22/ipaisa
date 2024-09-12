package com.Ipaisa.Entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Ticket_Setelement")
public class TicketSettelement {

	@Id
	@Column(name="setelementId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	
	@Column(name="SenderId")
	private String SenderId;
	@Column(name="Sender_Name")
	private String sendername;
	
	@Column(name="parentId")
	private String parentId;
	 
	@Column(name="Parent_Name")
	private String parentname;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="dateTime")
	private LocalDateTime dateTime;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="Status")
	private String status;

	@OneToOne
	@JoinColumn(name="ticket_id")
	private TicketRaise ticketId;
	
	
	public TicketSettelement() {
	}
	

	public TicketSettelement(String SenderId, String sendername, String parentId, String parentname, Double amount,
			String remark ,TicketRaise ticketId) {
		super();
	
		this.SenderId = SenderId;
		this.sendername=sendername;
		this.parentId = parentId;
		this.parentname=parentname;
		this.amount = amount;
		this.dateTime = LocalDateTime.now();
		this.remark = remark;
		this.status = status;
		this.ticketId = ticketId;
		
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenderId() {
		return SenderId;
	}


	public void setSenderId(String senderId) {
		SenderId = senderId;
	}


	public String getSendername() {
		return sendername;
	}


	public void setSendername(String sendername) {
		this.sendername = sendername;
	}


	public String getParentname() {
		return parentname;
	}


	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TicketRaise getTicketId() {
		return ticketId;
	}

	public void setTicketId(TicketRaise ticketId) {
		this.ticketId = ticketId;
	}
	
	
	
}


