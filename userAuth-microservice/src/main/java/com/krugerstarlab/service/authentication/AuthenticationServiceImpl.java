package com.krugerstarlab.service.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.entity.security_model.SecurityUser;
import com.krugerstarlab.exception.InvalidUsernameOrPasswordException;
import com.krugerstarlab.security.JWTStore;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	

	private final UserDetailsService userDetailsService;
	
	private final  JWTStore jwtStore;
	
	private final AuthenticationManager authManager;
	
	
	private static Logger logger=LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	
	public AuthenticationServiceImpl(UserDetailsService userDetailsService, JWTStore jwtStore,AuthenticationManager authManager) {
		this.userDetailsService = userDetailsService;
		this.jwtStore = jwtStore;
		this.authManager=authManager;
	}




	@Override
	public String authenticateUser(LoginRequest request) throws InvalidUsernameOrPasswordException {
		logger.debug("Authenticating the loginRequest");
		try {
			SecurityUser user=(SecurityUser) userDetailsService.loadUserByUsername(request.getEmail());
			logger.info(user.getRole().toString());
			
		//validate the password if it matches the provided one
		//Authentication manager will internally call the userDetailsServiceImpl.loadUserByUserName
		//then it will validate the password of the securityUser and the password of the request
		Authentication auth= authManager.
				authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
		//once password is validated we will update the securityContextHolder 
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return jwtStore.generateToken(auth);
		
		}catch (UsernameNotFoundException e) {
            throw new InvalidUsernameOrPasswordException("Invalid email or password");
        } catch (AuthenticationException e) {
            throw new InvalidUsernameOrPasswordException("Invalid email or password");
        }

	}
	


}
