//package com.lpaisa.JWTTokenValidation;
//
//import java.util.Date;
//
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
//@Component
//public class AuthValidation implements IAuthValidation	{
//	
//	
//	@Autowired
//	private UserRepositery userrepo;
//	
//	@Value("${secretkey}")
//    private String secret;
//	
//	 public  Boolean validateToken(String token, UserDetails userDetails) {
//	        final String username = getUsernameFromToken(token);
//	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	    }
//
//	
//	 
//	 
//	 public String getUsernameFromToken(String token) {
//	        return getClaimFromToken(token, Claims::getSubject);
//	    }
//	 
//	   public Boolean isTokenExpired(String token) {
//	        final Date expiration = getExpirationDateFromToken(token);
//	        return expiration.before(new Date());
//	    }
//	   
//	   public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//	        final Claims claims = getAllClaimsFromToken(token);
//	        return claimsResolver.apply(claims);
//	    }
//
//	    // For retrieving any information from token, we need the secret key
//	    public Claims getAllClaimsFromToken(String token) {
//	        return Jwts.parser()
//	                .setSigningKey(secret)
//	                .parseClaimsJws(token)
//	                .getBody();
//	    }
//	    
//	    public Date getExpirationDateFromToken(String token) {
//	        return getClaimFromToken(token, Claims::getExpiration);
//	    }
//
//	 
//}


