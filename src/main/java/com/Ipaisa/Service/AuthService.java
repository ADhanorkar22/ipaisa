package com.Ipaisa.Service;

import org.springframework.http.ResponseEntity;

import com.Ipaisa.Entitys.MobileNumber;
import com.Ipaisa.Entitys.Mpin;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.dto.AuthRequest;

public interface AuthService {
	
	 public ResponseEntity<?> Signin( AuthRequest request);
	 public User signinn(String mno);
	 public Boolean setmpin(Mpin mpin);
//	 public void setMobileNo(String mpin);
//	 ResponseEntity<?> getbyno(String mno);
//	Boolean getByMobileNo(String mobielno);
	// public Boolean mobielRepo(Mpin mpin);
	// public Boolean setmobilempin(Mpin mpin);
	public ResponseEntity<?> deletePartner(String uid);


}
