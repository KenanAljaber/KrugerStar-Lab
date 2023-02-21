package com.krugerstarlab.dto;

import org.springframework.http.HttpStatus;

import com.krugerstarlab.entity.member.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

	private UserProfile userProfile;

	private HttpStatus status;

	private String message;

	private String token;

}
