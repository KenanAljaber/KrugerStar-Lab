package com.krugerstarlab.dto;

import com.krugerstarlab.entity.member.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

	
	private Long id;
	private String firstName;
	private String lastName;
	private String photo;
	private Role role;
	
	
}
