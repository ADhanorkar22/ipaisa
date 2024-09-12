package com.Ipaisa.Entitys;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "EasebuzzPayin")
public class EasebuzzPayin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long id;

	@Column(name = "txnid", nullable = false)
	private String txnid;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "key_id")
	private String keyId; 

	@Column(name = "mode")
	private String mode;

	@Column(name = "unmappedstatus")
	private String unmappedstatus;

	@Column(name = "cardCategory")
	private String cardCategory;

	@Column(name = "addedon")
	private LocalDateTime addedon;

	@Column(name = "PG_TYPE")
	private String pgType;

	@Column(name = "bank_ref_num")
	private String bankRefNum;

	@Column(name = "bankcode")
	private String bankcode;

	@Column(name = "error")
	private String error;

	@Column(name = "error_Message")
	private String errorMessage;

	@Column(name = "name_on_card")
	private String nameOnCard;

	@Column(name = "upi_va")
	private String upiVa;

	@Column(name = "cardnum")
	private String cardnum;

	@Column(name = "issuing_bank")
	private String issuingBank;

	@Column(name = "easepayid")
	private String easepayid;

	@Column(name = "amount")
	private String amount;

	@Column(name = "net_amount_debit")
	private String netAmountDebit;

	@Column(name = "cash_back_percentage")
	private String cashBackPercentage;

	@Column(name = "deduction_percentage")
	private String deductionPercentage;

	@Column(name = "productinfo")
	private String productinfo;

	@Column(name = "card_type")
	private String cardType;

	@Column(name = "status")
	private String status;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "auth_code")
	private String authCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	public EasebuzzPayin() {
	}

	public EasebuzzPayin(Long id, String txnid, String firstname, String email, String phone, String key, String mode,
			String unmappedstatus, String cardCategory, LocalDateTime addedon, String paymentSource, String pgType,
			String bankRefNum, String bankcode, String error, String errorMessage, String nameOnCard, String upiVa,
			String cardnum, String issuingBank, String easepayid, String amount, String netAmountDebit,
			String cashBackPercentage, String deductionPercentage, String productinfo, String cardType, String status,
			String bankName, String authCode, User user) {
		super();
		this.id = id;
		this.txnid = txnid;
		this.firstname = firstname;
		this.email = email;
		this.phone = phone;
		this.keyId = key;
		this.mode = mode;
		this.unmappedstatus = unmappedstatus;
		this.cardCategory = cardCategory;
		this.addedon = addedon;

		this.pgType = pgType;
		this.bankRefNum = bankRefNum;
		this.bankcode = bankcode;
		this.error = error;
		this.errorMessage = errorMessage;
		this.nameOnCard = nameOnCard;
		this.upiVa = upiVa;
		this.cardnum = cardnum;
		this.issuingBank = issuingBank;
		this.easepayid = easepayid;
		this.amount = amount;
		this.netAmountDebit = netAmountDebit;
		this.cashBackPercentage = cashBackPercentage;
		this.deductionPercentage = deductionPercentage;
		this.productinfo = productinfo;
		this.cardType = cardType;
		this.status = status;
		this.bankName = bankName;
		this.authCode = authCode;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String setKeyId() {
		return keyId;
	}

	public void setKeyId(String key) {
		this.keyId = key;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUnmappedstatus() {
		return unmappedstatus;
	}

	public void setUnmappedstatus(String unmappedstatus) {
		this.unmappedstatus = unmappedstatus;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public LocalDateTime getAddedon() {
		return addedon;
	}

	public void setAddedon(LocalDateTime addedon) {
		this.addedon = addedon;
	}

	public String getPgType() {
		return pgType;
	}

	public void setPgType(String pgType) {
		this.pgType = pgType;
	}

	public String getBankRefNum() {
		return bankRefNum;
	}

	public void setBankRefNum(String bankRefNum) {
		this.bankRefNum = bankRefNum;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getUpiVa() {
		return upiVa;
	}

	public void setUpiVa(String upiVa) {
		this.upiVa = upiVa;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getIssuingBank() {
		return issuingBank;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	public String getEasepayid() {
		return easepayid;
	}

	public void setEasepayid(String easepayid) {
		this.easepayid = easepayid;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNetAmountDebit() {
		return netAmountDebit;
	}

	public void setNetAmountDebit(String netAmountDebit) {
		this.netAmountDebit = netAmountDebit;
	}

	public String getCashBackPercentage() {
		return cashBackPercentage;
	}

	public void setCashBackPercentage(String cashBackPercentage) {
		this.cashBackPercentage = cashBackPercentage;
	}

	public String getDeductionPercentage() {
		return deductionPercentage;
	}

	public void setDeductionPercentage(String deductionPercentage) {
		this.deductionPercentage = deductionPercentage;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "EasebuzzPayin [id=" + id + ", txnid=" + txnid + ", firstname=" + firstname + ", email=" + email
				+ ", phone=" + phone + ", keyId=" + keyId + ", mode=" + mode + ", unmappedstatus=" + unmappedstatus
				+ ", cardCategory=" + cardCategory + ", addedon=" + addedon + ", pgType=" + pgType + ", bankRefNum="
				+ bankRefNum + ", bankcode=" + bankcode + ", error=" + error + ", errorMessage=" + errorMessage
				+ ", nameOnCard=" + nameOnCard + ", upiVa=" + upiVa + ", cardnum=" + cardnum + ", issuingBank="
				+ issuingBank + ", easepayid=" + easepayid + ", amount=" + amount + ", netAmountDebit=" + netAmountDebit
				+ ", cashBackPercentage=" + cashBackPercentage + ", deductionPercentage=" + deductionPercentage
				+ ", productinfo=" + productinfo + ", cardType=" + cardType + ", status=" + status + ", bankName="
				+ bankName + ", authCode=" + authCode + ", user=" + user + "]";
	}

}
