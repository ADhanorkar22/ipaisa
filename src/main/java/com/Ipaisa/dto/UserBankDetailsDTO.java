package com.Ipaisa.dto;
public class UserBankDetailsDTO {
    private String id;
    private String bankName;
    private String branch;
    private Long accountNumber;
    private String ifsc;
    private String name;
    private UserDTO user;

    // Getters and Setters

   

    public UserBankDetailsDTO(String id, String bankName, String branch, Long accountNumber, String ifsc, String name,
			UserDTO user) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.branch = branch;
		this.accountNumber = accountNumber;
		this.ifsc = ifsc;
		this.name = name;
		this.user = user;
	}
    public String getId() {
    	return id;
    }

	public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public UserDTO getUser() {
        return user;
    }

    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUser(UserDTO user) {
        this.user = user;
    }

    // Inner class to represent UserDTO
    public static class UserDTO {
        private String userid;
        private String mobileNumber;
        private String fullName;

        
        
        public UserDTO(String userid, String mobileNumber, String fullName) {
			super();
			this.userid = userid;
			this.mobileNumber = mobileNumber;
			this.fullName = fullName;
		}

		// Getters and Setters

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
        
        
    }
}
