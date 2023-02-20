package com.krugerstarlab.service.authentication;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.exception.InvalidUsernameOrPasswordException;

public interface AuthenticationService {
	
	public String authenticateUser(LoginRequest request) throws InvalidUsernameOrPasswordException;

}
