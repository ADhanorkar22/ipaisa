package com.Ipaisa.entity;

public class DeeplinkRequest {
    private double amount;
    private String retailerName;
    private String retailerMobileNo;
    private String userName;
    private String userMobileNo;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerMobileNo() {
        return retailerMobileNo;
    }

    public void setRetailerMobileNo(String retailerMobileNo) {
        this.retailerMobileNo = retailerMobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }
}
