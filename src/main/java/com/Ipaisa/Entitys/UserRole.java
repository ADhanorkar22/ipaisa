package com.Ipaisa.Entitys;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "User_Role")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roll_id")
	private Integer id;

	@Column(name = "user_Role")
	public String userrole;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<User> users;

	@Column(name = "commission")
	private Double commission;

	public UserRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRole(Integer i) {
		super();
		this.id = i;
		// TODO Auto-generated constructor stub
	}

	public UserRole(Integer role_id, String name, List<User> users, Double commission) {
		super();
		this.id = role_id;
		this.userrole = name;
		this.users = users;
		this.commission = commission;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", userRole=" + userrole + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUserRole() {
		return userrole;
	}

	public void setUserRole(String userRole) {
		this.userrole = userRole;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

}
