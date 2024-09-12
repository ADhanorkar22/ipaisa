//package com.lpaisa.JWTTokenValidation;
//
//import java.util.Date;
//import java.util.function.Function;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.Ipaisa.Repository.UserRepositery;
////import com.lpaisa.Service.*;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import io.jsonwebtoken.Claims;
//
//public interface IAuthValidation {
//	
//	 public  Boolean validateToken(String token, UserDetails userDetails);
//	 public String getUsernameFromToken(String token);
//	   public Boolean isTokenExpired(String token);
//	   
//	   public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);
//	    public Claims getAllClaimsFromToken(String token);    
//	    public Date getExpirationDateFromToken(String token);
//
//}


