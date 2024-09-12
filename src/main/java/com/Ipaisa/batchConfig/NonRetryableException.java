package com.Ipaisa.batchConfig;

public class NonRetryableException extends Exception {
    public NonRetryableException(String message) {
        super(message);
    }
}