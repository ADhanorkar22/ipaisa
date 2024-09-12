package com.Ipaisa.Entitys;

import jakarta.validation.constraints.NotEmpty;

public class InstantPayBody {

    @NotEmpty
    private String name;

    @NotEmpty
    private String payeeaccountNumber;

    @NotEmpty
    private String bankIfsc;

    @NotEmpty
    private String transferMode;

    @NotEmpty
    private String transferAmount;

    // Default constructor
    public InstantPayBody() {}

    // Parameterized constructor
    public InstantPayBody(String name, String payeeaccountNumber, String bankIfsc, String transferMode, String transferAmount) {
        this.name = name;
        this.payeeaccountNumber = payeeaccountNumber;
        this.bankIfsc = bankIfsc;
        this.transferMode = transferMode;
        this.transferAmount = transferAmount;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayeeaccountNumber() {
        return payeeaccountNumber;
    }

    public void setPayeeaccountNumber(String payeeaccountNumber) {
        this.payeeaccountNumber = payeeaccountNumber;
    }

    public String getBankIfsc() {
        return bankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.bankIfsc = bankIfsc;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return "InstantPayBody [name=" + name + ", payeeaccountNumber=" + payeeaccountNumber + ", bankIfsc=" + bankIfsc
                + ", transferMode=" + transferMode + ", transferAmount=" + transferAmount + "]";
    }
}
