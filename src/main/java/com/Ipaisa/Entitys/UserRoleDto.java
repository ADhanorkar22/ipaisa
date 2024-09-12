package com.Ipaisa.Entitys;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRoleDto {
	
	private String id;
	
	private String userRole;

	private String status;
	
	private Double commission;

	public String getId() {
		return id;
	}

	public UserRoleDto() {
		
	}

	public UserRoleDto(String id, String userRole, String status, Double commission) {
		super();
		this.id = id;
		this.userRole = userRole;
		this.status = status;
		this.commission = commission;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}
	
	
	
}