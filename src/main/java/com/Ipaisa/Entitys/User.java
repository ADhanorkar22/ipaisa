package com.Ipaisa.Entitys;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User  implements Comparable<User> {

    @Id  
    @Column(name = "user_id")
    private String userid;

    @Column(name = "parent_Id")
    private String parentId;

    @Column(name = "first_Name")
    private String firstName;

    @Column(name = "middle_Name")
    private String middleName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "wallet_Balance")
    private Double walletBalance;

    @Column(name = "cash_Back_Balance")
    private Double cashBackbWallet;

    @Column(name = "contact_No")
    private String mobileNumber;

    @Column(name = "alternate_No")
    private Long alternateMobileNumber;

    @Column(name = "mpin")
    private String mpin;

    @Column(name = "adhar_Number")
    private String aadharNumber;

    @Column(name = "business_Name")
    private String bussinessName;

    @Column(name = "GST_No")
    private String gstin;

    @Column(name = "Dob")
    private LocalDate Dob;
    
    @Column(name = "Blk_pyout_accs")
    private String bulkPayout;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "comm_sur_type")
    private Comm_Sur comm_sur_type;
    
    @Column(name = "percentage")
    private Double percentage;
    

    @Enumerated(EnumType.STRING)
    @Column(name="category")
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Column(name="created_At")
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name="isFirstLogin")
    private IsFirstLogin isFirstLogin;
    
    @Enumerated(EnumType.STRING)
    @Column(name="is_deleted")
    private Deleted isDeleted;
   

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Role_Id")
	@JsonBackReference
    private UserRole role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EasebuzzPayin> easebuzztransactions = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<InstantPayOut> instanttransactions = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<UserBankDetails> userbankdetails;

	@Transient
	private String utype;

	@Transient
	private String userRole;

	public User() {
	}

	public User(String firstName,String parentId, String middleName, String lastName, LocalDate dob, String mobileNumber,
			Long alternateMobileNumber, String email, String aadharNumber, String bussinessName, String gstin,String bulkPayout
			,String comm_sur_type,Double percentage) {
		super();
		this.firstName = firstName;
		this.parentId=parentId;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.alternateMobileNumber = alternateMobileNumber;
		this.aadharNumber = aadharNumber;
		this.bussinessName = bussinessName;
		this.gstin = gstin;
		this.Dob = dob;
//		this.category = Category.valueOf(category);
		this.status = status.ACTIVE;
		this.walletBalance=0.0;
		this.bulkPayout=bulkPayout;
		this.comm_sur_type=Comm_Sur.valueOf(comm_sur_type);
		this.percentage=percentage;
		this.createdAt=LocalDateTime.now();
		this.isFirstLogin=isFirstLogin.TRUE;
		this.isDeleted=isDeleted.FALSE;
	
		

	}

	public User(String userid, String parentId, String firstName, String middleName, String lastName, String email,
			Double walletBalance, Double cashBackbWallet, String mobileNumber, Long alternateMobileNumber, String mpin,
			String aadharNumber, String bussinessName, String gstin, LocalDate dob, Category category,
			UserRole role, String utype) {
		super();
		this.userid = userid;
		this.parentId = parentId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.walletBalance = walletBalance;
		this.cashBackbWallet = cashBackbWallet;
		this.mobileNumber = mobileNumber;
		this.alternateMobileNumber = alternateMobileNumber;
		this.mpin = mpin;
		this.aadharNumber = aadharNumber;
		this.bussinessName = bussinessName;
		this.gstin = gstin;
		Dob = dob;
		this.category = category;
		this.status = status.INACTIVE;
		this.role = role;
		this.utype = role.getUserrole();
	}

	public User(String firstName, UserRole userRole) {
		this.firstName = firstName;
		this.role = userRole;
	}

	public User(String firstName, String userRole) {
		this.firstName = firstName;
		this.userRole = userRole;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public Double getCashBackbWallet() {
		return cashBackbWallet;
	}

	public void setCashBackbWallet(Double cashBackbWallet) {
		this.cashBackbWallet = cashBackbWallet;
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

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
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

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public LocalDate getDob() {
		return Dob;
	}

	public void setDob(LocalDate dob) {
		Dob = dob;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}
		

	public String getBulkPayout() {
		return bulkPayout;
	}

	public void setBulkPayout(String bulkPayout) {
		this.bulkPayout = bulkPayout;
	}

	public String getComm_sur_type() {
		return  comm_sur_type.toString();
	}

	public void setComm_sur_type(String comm_sur_type) {
		this.comm_sur_type = Comm_Sur.valueOf(comm_sur_type);
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Deleted getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Deleted isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<EasebuzzPayin> getEasebuzztransactions() {
		return easebuzztransactions;
	}

	public void setEasebuzztransactions(List<EasebuzzPayin> easebuzztransactions) {
		this.easebuzztransactions = easebuzztransactions;
	}

	public List<InstantPayOut> getInstanttransactions() {
		return instanttransactions;
	}

	public void setInstanttransactions(List<InstantPayOut> instanttransactions) {
		this.instanttransactions = instanttransactions;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void setComm_sur_type(Comm_Sur comm_sur_type) {
		this.comm_sur_type = comm_sur_type;
	}
	
	

	public IsFirstLogin getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(IsFirstLogin isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", parentId=" + parentId + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", email=" + email + ", walletBalance=" + walletBalance
				+ ", cashBackbWallet=" + cashBackbWallet + ", mobileNumber=" + mobileNumber + ", alternateMobileNumber="
				+ alternateMobileNumber + ", mpin=" + mpin + ", aadharNumber=" + aadharNumber + ", bussinessName="
				+ bussinessName + ", gstin=" + gstin + ", Dob=" + Dob + ", bulkPayout=" + bulkPayout
				+ ", comm_sur_type=" + comm_sur_type + ", percentage=" + percentage + ", category=" + category
				+ ", status=" + status + ", role=" + role + ", utype=" + utype + ", userRole=" + userRole + "]";
	}
	
	
	
	

	@Override
	public int compareTo(User o) {
		
		return this.createdAt.compareTo(o.createdAt);
	}


	
	

}