package com.Ipaisa.Entitys;

//{
//    "firstName": "Yash",
//    "middleName": "Anil",
//    "lastName": "Suryawanshi",
//    "dob": "2024-06-01",
//    "mobileNumber": "0837805408",
//    "alternateMobileNumber": "0837805408",
//    "email": "yashsuryawanshi2275@gmail.com",
//    "aadharNumber": "12341234123411",
//    "bussinessName": "Kolhapur",
//    "userType": "channelpartner",
//    "gstin": "123451234512345",
//    "category": "fixed",
//   
//    "state": {
//        "name": "Maharashtra",
//        "isoCode": "MH",
//        "countryCode": "IN",
//        "latitude": "19.75147980",
//        "longitude": "75.71388840"
//    },
//    "country": {
//        "name": "India",
//        "isoCode": "IN",
//        "flag": ":flag-in:",
//        "phonecode": "91",
//        "currency": "INR",
//        "latitude": "20.00000000",
//        "longitude": "77.00000000",
//        "timezones": [
//            {
//                "zoneName": "Asia/Kolkata",
//                "gmtOffset": 19800,
//                "gmtOffsetName": "UTC+05:30",
//                "abbreviation": "IST",
//                "tzName": "Indian Standard Time"
//            }
//        ]
//    },
//    "address": "CSBIER CHOWK",
//    "pincode": "416012"
//}





public class UsersDetail {
	private String firstName;
	private String parentId;
	private String middleName;
	private String lastName;
	private String dob;
	private String mobileNumber;
	private Long alternateMobileNumber;
	private String email;
	private String aadharNumber;
	private String bussinessName;
	private String utype;
	private String gstin;
	private String category;
	private String address;
	private String pincode;
	private String state;
	private String country;
	private String district;
	private String commissionsurcharge;
	private Double percentage;
	private String  bulkPayout;
	//private City city;
	
//
//	public UsersDetail(String firstName, String middleName, String lastName, String dob, String mobileNumber,
//			Long alternateMobileNumber, String email, String aadharNumber, String businessName, String utype,
//			String gstin, String category, String address,String pincode,State state,Country country,City city) {
//		super();
//		this.firstName = firstName;
//		this.middleName = middleName;
//		this.lastName = lastName;
//		this.dob = dob;
//		this.mobileNumber = mobileNumber;
//		this.alternateMobileNumber = alternateMobileNumber;
//		this.email = email;
//		this.aadharNumber = aadharNumber;
//		this.bussinessName = businessName;
//		this.utype = utype;
//		this.gstin = gstin;
//		this.category = category;
//		this.address=address;
//		this.pincode=pincode;
//		this.state=state;
//		this.country=country;
//		this.city=city;
//	}

	
	

	
	public String getFirstName() {
		return firstName;
	}
	public UsersDetail(String firstName, String parentid, String middleName, String lastName, String dob, String mobileNumber,
			Long alternateMobileNumber, String email, String aadharNumber, String bussinessName, String utype,
			String gstin, String category, String address, String pincode, String state, String country, String district,
			String commissionsurcharge, Double percentage, String bulkPayout) {
		super();
		this.firstName = firstName;
		this.parentId=parentid;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dob = dob;
		this.mobileNumber = mobileNumber;
		this.alternateMobileNumber = alternateMobileNumber;
		this.email = email;
		this.aadharNumber = aadharNumber;
		this.bussinessName = bussinessName;
		this.utype = utype;
		this.gstin = gstin;
		this.category = category;
		this.address = address;
		this.pincode = pincode;
		this.state = state;
		this.country = country;
		this.district = district;
		this.commissionsurcharge = commissionsurcharge;
		this.percentage = percentage;
		this.bulkPayout = bulkPayout;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(Long alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}



	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	
	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	
	
	public String getCommissionsurcharge() {
		return commissionsurcharge;
	}
	public void setCommissionsurcharge(String commissionsurcharge) {
		this.commissionsurcharge = commissionsurcharge;
	}
	public String getBulkPayout() {
		return bulkPayout;
	}
	public void setBulkPayout(String bulkPayout) {
		this.bulkPayout = bulkPayout;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	

	


	
}