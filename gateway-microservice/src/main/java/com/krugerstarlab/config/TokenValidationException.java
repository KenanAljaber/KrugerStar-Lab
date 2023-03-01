package com.krugerstarlab.config;

import org.springframework.http.ResponseEntity;

public class TokenValidationException extends RuntimeException {
	
	

	public TokenValidationException(String message ) {
		super(message);
		
	}

	
	
	

}
