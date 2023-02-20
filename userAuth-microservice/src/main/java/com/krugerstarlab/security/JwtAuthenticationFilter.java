package com.krugerstarlab.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ctc.wstx.util.StringUtil;
import com.krugerstarlab.controller.MemberController;
import com.krugerstarlab.entity.security_model.SecurityUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTStore jwtStore;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//get the token from the header of the request
		String token= parseJwt(request);
		//validate whether the token is not null and is valid
		if(token!=null && jwtStore.validateToken(token)) {
			String email=jwtStore.getEmailFromToken(token);
			//getting the securityUser from the implementation of the userDetailsService 
			 SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(email);
			 //validate that the password provided is the same as the password found in db
		      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                      userDetails, null, userDetails.getAuthorities());
		      //update the securityContextHolder 
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authentication);
              
              logger.debug("request is authenticated "+authentication.getDetails());
		}
		}catch(Exception e) {
			logger.error("Cannot set user authentication: " + e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	public String parseJwt (HttpServletRequest req) {
		String headerAuth= req.getHeader("Authorization");
		
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7,headerAuth.length());
		}
		return null;
	}

}
