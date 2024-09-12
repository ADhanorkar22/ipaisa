package com.Ipaisa.Entitys;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name="user_kyc")
public class UserKycDetail {
	
		@Id
	    private String uid;  
	    @Column(name = "aadhaar_file_path")
	    @Nullable
	    private String aadhaarFilePath;

	    @Column(name = "pan_file_path")
	    @Nullable
	    private String panFilePath;

	    @Column(name = "Photo_file_path")
	    @Nullable
	    private String passportFilePath;
	    
	    @Column(name="agreement_file_path")
	    @Nullable
	    private String agreementFilePath;

	    @Enumerated(EnumType.STRING)
	    @Column
	    @Nullable
		private Status KYC_status;
	   
	    @OneToOne
	    @JoinColumn(name="u_id")
	    @MapsId
	    private User user;
	    
	    public UserKycDetail() {
		}

		public UserKycDetail(String aadhaarFilePath, String panFilePath, String passportFilePath,String agreementFilePath, User user) {
			super();
			this.aadhaarFilePath = aadhaarFilePath;
			this.panFilePath = panFilePath;
			this.passportFilePath = passportFilePath;
			KYC_status = Status.PENDING ;
			this.user = user;
			this.agreementFilePath=agreementFilePath;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getAadhaarFilePath() {
			return aadhaarFilePath;
		}

		public void setAadhaarFilePath(String aadhaarFilePath) {
			this.aadhaarFilePath = aadhaarFilePath;
		}

		public String getPanFilePath() {
			return panFilePath;
		}

		public void setPanFilePath(String panFilePath) {
			this.panFilePath = panFilePath;
		}

		public String getPassportFilePath() {
			return passportFilePath;
		}

		public void setPassportFilePath(String passportFilePath) {
			this.passportFilePath = passportFilePath;
		}

		public Status getKYC_status() {
			return KYC_status;
		}

		public void setKYC_status(Status kYC_status) {
			KYC_status = kYC_status;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getAgreementFilePath() {
			return agreementFilePath;
		}

		public void setAgreementFilePath(String agreementFilePath) {
			this.agreementFilePath = agreementFilePath;
		}

		@Override
		public String toString() {
			return "UserKycDetail [uid=" + uid + ", aadhaarFilePath=" + aadhaarFilePath + ", panFilePath=" + panFilePath
					+ ", passportFilePath=" + passportFilePath + ", agreementFilePath=" + agreementFilePath
					+ ", KYC_status=" + KYC_status + ", user=" + user + "]";
		}
		
		
		

	
}
