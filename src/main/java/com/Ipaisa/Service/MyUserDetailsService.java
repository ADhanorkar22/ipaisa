package com.Ipaisa.Service;

import java.beans.JavaBean;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//import com.lpaisa.Entitys;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.UserRepositery;

import jakarta.transaction.Transactional;



///
@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositery userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByMobileNumber(username);
        System.out.println("heree");
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }else {
        	System.out.println("not found");
        }
        return new UserPrincipal(user);
    }
}
