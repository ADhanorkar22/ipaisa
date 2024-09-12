package com.Ipaisa.Responses;

import java.util.List;

import com.Ipaisa.Entitys.User;

public class UserListResponse {
    private int status;
    private String message;
    private List<User> users;

    // Constructors, getters, setters
    // Omitted for brevity

    UserListResponse(){}
    public UserListResponse(int status, String message, List<User> users) {
        this.status = status;
        this.message = message;
        this.users = users;
    }

    // Static methods for common response scenarios

    public static UserListResponse success(String message, List<User> users) {
        return new UserListResponse(200, message, users);
    }

    public static UserListResponse notFound(String message) {
        return new UserListResponse(404, message, null);
    }
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
    
}