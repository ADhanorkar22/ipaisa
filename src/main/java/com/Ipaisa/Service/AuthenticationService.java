package com.Ipaisa.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Ipaisa.Jwt_Utils.JwtUtils;

import java.util.Optional;


import lombok.extern.slf4j.Slf4j;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MyUserDetailsService userDetailsService;

    public Optional<UserDetails> authenticateUserByToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String cleanedToken = token.substring(7);
            logger.info("Attempting to authenticate user with token: {}", cleanedToken);

            if (jwtUtils.validateJwtToken(cleanedToken)) {
                String username = jwtUtils.getUserNameFromJwtToken(cleanedToken);
                logger.info("Token validated successfully. Fetching user details for: {}", username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                return Optional.of(userDetails);
            } else {
                logger.warn("Invalid JWT token provided: {}", cleanedToken);
            }
        } else {
            if (token == null) {
                logger.warn("No JWT token provided in the request");
            } else {
                logger.warn("JWT token does not start with 'Bearer ': {}", token);
            }
        }

        return Optional.empty(); 
    }
}
