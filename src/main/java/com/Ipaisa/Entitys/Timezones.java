package com.Ipaisa.Entitys;

public class Timezones {

	private String zoneName;
	private Integer gmtOffset;
	private String gmtOffsetName;
	private String abbreviation;
	private String tzName;

	public Timezones() {
		// TODO Auto-generated constructor stub
	}

	public Timezones(String zoneName, Integer gmtOffset, String gmtOffsetName, String abbreviation, String tzName) {
		super();
		this.zoneName = zoneName;
		this.gmtOffset = gmtOffset;
		this.gmtOffsetName = gmtOffsetName;
		this.abbreviation = abbreviation;
		this.tzName = tzName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getGmtOffset() {
		return gmtOffset;
	}

	public void setGmtOffset(Integer gmtOffset) {
		this.gmtOffset = gmtOffset;
	}

	public String getGmtOffsetName() {
		return gmtOffsetName;
	}

	public void setGmtOffsetName(String gmtOffsetName) {
		this.gmtOffsetName = gmtOffsetName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getTzName() {
		return tzName;
	}

	public void setTzName(String tzName) {
		this.tzName = tzName;
	}

}
