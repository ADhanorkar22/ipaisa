package com.Ipaisa.dto;

public class UpUser {
	    private String firstName;
	    private String middleName;
	    private String lastName;
	    private String businessName;
	    private String email;
	    private String mobileNumber;
	    private String address;
	    private String dob; 
	    
	    public UpUser() {
			// TODO Auto-generated constructor stub
		}

		public UpUser(String firstName, String middleName, String lastName, String businessName, String email,
				String mobileNumber, String address, String dob) {
			super();
			this.firstName = firstName;
			this.middleName = middleName;
			this.lastName = lastName;
			this.businessName = businessName;
			this.email = email;
			this.mobileNumber = mobileNumber;
			this.address = address;
			this.dob = dob;
		}

		public String getFirstName() {
			return firstName;
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

		public String getBusinessName() {
			return businessName;
		}

		public void setBusinessName(String businessName) {
			this.businessName = businessName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDob() {
			return dob;
		}

		public void setDob(String dob) {
			this.dob = dob;
		}
	    
	   
}
