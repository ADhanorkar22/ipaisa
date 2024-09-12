//package com.lpaisa.filters;
//
//import java.io.IOException;
////
////import javax.servlet.FilterChain;
////import javax.servlet.ServletException;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.Ipaisa.Service.MyUserDetailsService;
//import com.lpaisa.Jwt_Utils.JwtUtils;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//@Slf4j
//@Component
//public class JWTRequestFilter extends OncePerRequestFilter {
//	@Autowired
//	private JwtUtils utils;
////	@Autowired
////	private UserDetailsService userDetailsService;
//	@Autowired
//	private MyUserDetailsService userDetailsService;
//
////	@Override
////	protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
////			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
////			throws jakarta.servlet.ServletException, IOException {
////	//	log.info("in once per request filter");
////		// get authorization header n check if not null n starting with Bearer
////		String header = request.getHeader("Authorization");
////		if (header != null && header.startsWith("Bearer ")) {
////			// Bearer token present --> extract n validate it
////			String token = header.substring(7);
////			if (utils.validateJwtToken(token)) {
////				// valid token --> extract user name from the token
////				String userName = utils.getUserNameFromJwtToken(token);
////				
////				if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////					// load user details from UserDetailsService
////					UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
////					// create Authentication object , wrapping user details lifted from DB
////					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
////							userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
////					//set details in auth object
////		//			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//////					Save this authentication token in the sec ctx.
////					SecurityContextHolder.getContext().setAuthentication(authentication);
////				}
////				else
////				//	log.info("user name null or authentication already set , username {}",userName);
////						System.out.println("here");
////			}
////		} else
////			//log.error("Request header DOES NOT contain a Bearer Token");
////		//pass the request to the next filter in the chain
////		filterChain.doFilter(request, response);
////
////	}
////	
//	
//	////
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//	        throws ServletException, IOException {
//	    String header = request.getHeader("Authorization");
//	    System.out.println("JWTRequestFilter: Processing request to " + request.getRequestURI());
//	    
//	    if (header != null && header.startsWith("Bearer ")) {
//	        String token = header.substring(7);
//	        if (utils.validateJwtToken(token)) {
//	            String userName = utils.getUserNameFromJwtToken(token);
//	            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//	                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
//	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//	                        userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
//	                SecurityContextHolder.getContext().setAuthentication(authentication);
//	            }
//	        }
//	    }
//	    chain.doFilter(request, response);
//	}
//
//	
//
//
//	
////	@Override
////	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
////	    String path = request.getRequestURI();
////	    boolean shouldNotFilter = path.startsWith("/auth/") ||
////	                              path.startsWith("/auth/signin")||
////	    							path.startsWith("/auth/**");
////	    
////	                              //path.startsWith("/ipaisa/tickets");
////	    System.out.println("JWTRequestFilter: Should not filter request to " + path + ": " + shouldNotFilter);
////	    return shouldNotFilter;
////	}
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//	    String path = request.getRequestURI();
//	    boolean shouldNotFilter = path.startsWith("/auth/") || path.equals("/auth/signin");
//	    log.info("JWTRequestFilter: Should not filter request to {}: {}", path, shouldNotFilter);
//	    return shouldNotFilter;
//	}
//		
//	
//
//}



package com.Ipaisa.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JWTRequestFilter.class);
    @Autowired
    private JwtUtils utils;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        log.info("JWTRequestFilter: Processing request to {}", request.getRequestURI());

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (utils.validateJwtToken(token)) {
                String userName = utils.getUserNameFromJwtToken(token);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean shouldNotFilter = path.startsWith("/auth/") || path.equals("/auth/signin");
        log.info("JWTRequestFilter: Should not filter request to {}: {}", path, shouldNotFilter);
        return shouldNotFilter;
    }
}
