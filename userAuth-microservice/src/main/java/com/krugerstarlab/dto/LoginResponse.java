package com.krugerstarlab.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.krugerstarlab.entity.member.Role;
import com.krugerstarlab.entity.security_model.UserDetailsRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

	@JsonInclude(Include.NON_NULL)
	private UserProfile userProfile;

	private HttpStatus status;

	private String message;

	@JsonInclude(Include.NON_NULL)
	private String token;
	
	private UserDetailsRole userRole;

}
