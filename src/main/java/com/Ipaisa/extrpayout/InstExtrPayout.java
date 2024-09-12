package com.Ipaisa.extrpayout;





import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "instExterPayout")
public class InstExtrPayout {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "statuscode")
	    private String statuscode;

	    @Column(name = "actcode")
	    private String actcode;

	    @Column(name = "status")
	    private String status;

	    @Column(name = "external_ref")
	    private String externalRef;

	    @Column(name = "pool_reference_id")
	    private String poolReferenceId;

	    @Column(name = "txn_value")
	    private String txnValue;

	    @Column(name = "txn_reference_id")
	    private String txnReferenceId;

	    @Column(name = "pool_account")
	    private String poolAccount;

	    @Column(name = "pool_opening_bal")
	    private String poolOpeningBal;

	    @Column(name = "pool_mode")
	    private String poolMode;

	    @Column(name = "pool_amount")
	    private String poolAmount;

	    @Column(name = "pool_closing_bal")
	    private String poolClosingBal;

	    @Column(name = "payer_account")
	    private String payerAccount;

	    @Column(name = "payer_name")
	    private String payerName;

	    @Column(name = "payee_account")
	    private String payeeAccount;

	    @Column(name = "payee_name")
	    private String payeeName;

	    @Column(name = "timestamp")
	    private LocalDateTime timestamp;

	    @Column(name = "ipay_uuid")
	    private String ipayUuid;

	    @Column(name = "orderid")
	    private String orderid;

	    @Column(name = "environment")
	    private String environment;
//	    
//	    @Column(name="wallet_Opn_Balc")
//	    private Double walletOpeningBalance;
//	    
//	    @Column(name="wallet_Cls_Balc")
//	    private Double walletClosingBalance;
//	    
//	  
	    
	 



		

		


		public InstExtrPayout(Long id, String statuscode, String actcode, String status, String externalRef,
				String poolReferenceId, String txnValue, String txnReferenceId, String poolAccount,
				String poolOpeningBal, String poolMode, String poolAmount, String poolClosingBal, String payerAccount,
				String payerName, String payeeAccount, String payeeName, LocalDateTime timestamp, String ipayUuid,
				String orderid, String environment) {
			super();
			this.id = id;
			this.statuscode = statuscode;
			this.actcode = actcode;
			this.status = status;
			this.externalRef = externalRef;
			this.poolReferenceId = poolReferenceId;
			this.txnValue = txnValue;
			this.txnReferenceId = txnReferenceId;
			this.poolAccount = poolAccount;
			this.poolOpeningBal = poolOpeningBal;
			this.poolMode = poolMode;
			this.poolAmount = poolAmount;
			this.poolClosingBal = poolClosingBal;
			this.payerAccount = payerAccount;
			this.payerName = payerName;
			this.payeeAccount = payeeAccount;
			this.payeeName = payeeName;
			this.timestamp = timestamp;
			this.ipayUuid = ipayUuid;
			this.orderid = orderid;
			this.environment = environment;
//			this.walletOpeningBalance = walletOpeningBalance;
//			this.walletClosingBalance = walletClosingBalance;
		}






		public InstExtrPayout() {
			// TODO Auto-generated constructor stub
		}



		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getStatuscode() {
			return statuscode;
		}

		public void setStatuscode(String statuscode) {
			this.statuscode = statuscode;
		}

		public String getActcode() {
			return actcode;
		}

		public void setActcode(String actcode) {
			this.actcode = actcode;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getExternalRef() {
			return externalRef;
		}

		public void setExternalRef(String externalRef) {
			this.externalRef = externalRef;
		}

		public String getPoolReferenceId() {
			return poolReferenceId;
		}

		public void setPoolReferenceId(String poolReferenceId) {
			this.poolReferenceId = poolReferenceId;
		}

		public String getTxnValue() {
			return txnValue;
		}

		public void setTxnValue(String txnValue) {
			this.txnValue = txnValue;
		}

		public String getTxnReferenceId() {
			return txnReferenceId;
		}

		public void setTxnReferenceId(String txnReferenceId) {
			this.txnReferenceId = txnReferenceId;
		}

		public String getPoolAccount() {
			return poolAccount;
		}

		public void setPoolAccount(String poolAccount) {
			this.poolAccount = poolAccount;
		}

		public String getPoolOpeningBal() {
			return poolOpeningBal;
		}

		public void setPoolOpeningBal(String poolOpeningBal) {
			this.poolOpeningBal = poolOpeningBal;
		}

		public String getPoolMode() {
			return poolMode;
		}

		public void setPoolMode(String poolMode) {
			this.poolMode = poolMode;
		}

		public String getPoolAmount() {
			return poolAmount;
		}

		public void setPoolAmount(String poolAmount) {
			this.poolAmount = poolAmount;
		}

		public String getPoolClosingBal() {
			return poolClosingBal;
		}

		public void setPoolClosingBal(String poolClosingBal) {
			this.poolClosingBal = poolClosingBal;
		}

		public String getPayerAccount() {
			return payerAccount;
		}

		public void setPayerAccount(String payerAccount) {
			this.payerAccount = payerAccount;
		}

		public String getPayerName() {
			return payerName;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public String getPayeeAccount() {
			return payeeAccount;
		}

		public void setPayeeAccount(String payeeAccount) {
			this.payeeAccount = payeeAccount;
		}

		public String getPayeeName() {
			return payeeName;
		}

		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public String getIpayUuid() {
			return ipayUuid;
		}

		public void setIpayUuid(String ipayUuid) {
			this.ipayUuid = ipayUuid;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public String getEnvironment() {
			return environment;
		}

		public void setEnvironment(String environment) {
			this.environment = environment;
		}



		
    
}
