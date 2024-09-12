package com.Ipaisa.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Providers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String providerName;
	
	public Providers() {
		// TODO Auto-generated constructor stub
	}

	public Providers(Long id, String providerName) {
		super();
		this.id = id;
		this.providerName = providerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	@Override
	public String toString() {
		return "Providers [id=" + id + ", providerName=" + providerName + "]";
	}
	
	

}
