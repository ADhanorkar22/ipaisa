package com.Ipaisa.Entitys;

import java.util.List;

public class BulkPaymentRequest {
    private List<InstantPayBody> payments;

    public BulkPaymentRequest() {}

    public BulkPaymentRequest(List<InstantPayBody> payments) {
        this.payments = payments;
    }

    public List<InstantPayBody> getPayments() {
        return payments;
    }

    public void setPayments(List<InstantPayBody> payments) {
        this.payments = payments;
    }
}
