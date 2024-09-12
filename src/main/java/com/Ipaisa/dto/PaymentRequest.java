package com.Ipaisa.dto;

public class PaymentRequest {
    private String txnid;
    private String amount;
    private String productinfo;
    private String firstname;
    private String phone;
    private String email;
    private String surl;
    private String furl;

    // Getters and Setters
    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

	@Override
	public String toString() {
		return "PaymentRequest [txnid=" + txnid + ", amount=" + amount + ", productinfo=" + productinfo + ", firstname="
				+ firstname + ", phone=" + phone + ", email=" + email + ", surl=" + surl + ", furl=" + furl + "]";
	}
    
}
