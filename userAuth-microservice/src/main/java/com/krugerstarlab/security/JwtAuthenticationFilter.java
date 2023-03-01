package com.krugerstarlab.security;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.krugerstarlab.entity.security_model.SecurityUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JWTStore jwtStore;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	logger.info("[+] Method: {}, Path: {}", request.getMethod(), request.getRequestURI());
    	//if it is a path that does not require an authentication
        try {
        	if(!requierAuth(request.getRequestURI())) {
        		logger.info("[+] Request does not need a token");
                filterChain.doFilter(request, response);
                return ;
        	}
            String token = extractTokenFromRequest(request);

            if (StringUtils.hasText(token) && jwtStore.validateToken(token)) {
            	logger.info("[+] Token is valid");
                String email = jwtStore.getEmailFromToken(token);
                SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.debug("[+] Request is authenticated: {}", authentication.getDetails());
            } else {
                throw new InvalidTokenException("Token is not valid");
            }
        	
        } catch (InvalidTokenException e) {
            logger.error("[-] Cannot set user authentication: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
        	logger.info("[+] Extracted a token from the request header");
            return headerAuth.substring(7);
        }
        logger.error("[-] Request does not have any tokens");
        return null;
    }

    private static class InvalidTokenException extends Exception {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
    
	private boolean requierAuth(String path) {
		List<String> authorizedPaths = List.of(

				"/api/v1/users/auth/login", "/api/v1/users/auth/signup");

		return !authorizedPaths.contains(path);
	}
    
 
}
