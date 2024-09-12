package com.Ipaisa.CustomExceptions;

import com.Ipaisa.dto.AuthResp;

public class ApiResponse<T> {
    private String message;
    private boolean success;
    private AuthResp response;
    private T data;

    public ApiResponse() { }

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(AuthResp response) {
        this.response = response;
        this.success = true; // Assuming success is true if AuthResp is provided
    }

    public ApiResponse(String string, boolean b, AuthResp authResp) {
        this.message = string;
        this.success = true;
        this.response = authResp;
    }

    public ApiResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AuthResp getResp() {
        return response;
    }

    public void setResp(AuthResp response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
