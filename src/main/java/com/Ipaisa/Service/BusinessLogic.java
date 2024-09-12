package com.Ipaisa.Service;

import java.time.LocalDate;

import com.Ipaisa.Entitys.AddressDTO;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.UserKycDetail;
import com.Ipaisa.Entitys.UserRole;
import com.Ipaisa.Entitys.UsersAddress;
import com.Ipaisa.Entitys.UsersDetail;
import com.Ipaisa.Repository.AddressRepositery;
import com.Ipaisa.Repository.RollsRepositery;
import com.Ipaisa.Repository.UserRepositery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BusinessLogic {

	@Autowired
    private PasswordEncoder enc;

	public User getUserInstance(UsersDetail ud, RollsRepositery rollrepo, UserRepositery userRepo,AddressRepositery addRepo,String parentid) {
		User user = new User(ud.getFirstName(), parentid, ud.getMiddleName(), ud.getLastName(), LocalDate.parse(ud.getDob()),
			ud.getMobileNumber(), ud.getAlternateMobileNumber(), ud.getEmail(), ud.getAadharNumber(),
				ud.getBussinessName(), ud.getGstin(),ud.getBulkPayout(),ud.getCommissionsurcharge(),ud.getPercentage());
		String type = ud.getUtype();
		System.out.println("type " + type);
		String adharno = ud.getAadharNumber();
		System.out.println(adharno);
		String id = adharno.substring(8);
		switch (type.toUpperCase()) {
		case "ADMIN":
			user.setUserid("AA" + id);
			break;
		case "CHANNELPARTNER":
			user.setUserid("CP" + id);
			break;
		case "SUPERDISTRIBUTOR":
			user.setUserid("SD" + id);
			break;
		case "MASTERDISTRIBUTOR":
			user.setUserid("MD" + id);
			break;
		case "AREADISTRIBUTOR":
			user.setUserid("AD" + id);
			break;
		case "RETAILER":
			user.setUserid("RE" + id);
			break;
		default:
			throw new IllegalArgumentException("Invalid user type: " + type);
		}

		user.setCategory(user.getCategory().valueOf(ud.getCategory().toUpperCase()));
		UserRole userRole = rollrepo.findByUserrole(ud.getUtype());
		if (userRole != null) {
			System.out.println("msg " + userRole);
		}

		System.out.println(userRole);

		user.setRole(userRole);
		UsersAddress address = new UsersAddress(ud.getCountry(), ud.getState(),ud.getAddress(),Integer.parseInt( ud.getPincode()),ud.getDistrict());
		address.setUser(user);
		
		addRepo.save(address);

		userRepo.save(user);
		System.out.println(user);

		return user;
	}

	public static UsersAddress getUserAddressInstance(UsersDetail ud,User u,AddressRepositery addRepo) {
//		UsersAddress address = new UsersAddress(ud.getState().getName(),ud.getState().getName(),ud.getAddress(),Integer.parseInt( ud.getPincode()));;
//		address.setUser(u);
//		
//		addRepo.save(address);
//		
		return null;

	}
	
	

	public static UserKycDetail getUserUserKycDetails(UsersDetail ud) {

//		UserKycDetail uk = new UserKycDetail();
//
//		uk.setAdharno(ud.getAdharno());
//		uk.setPanNo(ud.getPanno());
		return null;

	}

}