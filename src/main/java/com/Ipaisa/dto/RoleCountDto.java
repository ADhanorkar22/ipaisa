package com.Ipaisa.dto;

public class RoleCountDto {
	private Integer channelPartner;
	private Integer superDistributer;
	private Integer masterDistributer;
	private Integer areaDistributer;
	private Integer retailer;
	private Integer user;
	
	
	public RoleCountDto() {
		// TODO Auto-generated constructor stub
	}
	public RoleCountDto(Integer channelPartner, Integer superDistributer, Integer masterDistributer,
			Integer areaDistributer, Integer retailer, Integer user) {
		super();
		this.channelPartner = channelPartner;
		this.superDistributer = superDistributer;
		this.masterDistributer = masterDistributer;
		this.areaDistributer = areaDistributer;
		this.retailer = retailer;
		this.user = user;
	}
	public Integer getChannelPartner() {
		return channelPartner;
	}
	public void setChannelPartner(Integer channelPartner) {
		this.channelPartner = channelPartner;
	}
	public Integer getSuperDistributer() {
		return superDistributer;
	}
	public void setSuperDistributer(Integer superDistributer) {
		this.superDistributer = superDistributer;
	}
	public Integer getMasterDistributer() {
		return masterDistributer;
	}
	public void setMasterDistributer(Integer masterDistributer) {
		this.masterDistributer = masterDistributer;
	}
	public Integer getAreaDistributer() {
		return areaDistributer;
	}
	public void setAreaDistributer(Integer areaDistributer) {
		this.areaDistributer = areaDistributer;
	}
	public Integer getRetailer() {
		return retailer;
	}
	public void setRetailer(Integer retailer) {
		this.retailer = retailer;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	
	
	
}