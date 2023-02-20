package com.krugerstarlab.service.member;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.security_model.SecurityUser;

public interface MemberService {

	// Create a new member
	public Member createMember(Member member);

	// Retrieve a member by ID
	public Member getMemberById(Long id);

	// Retrieve a member by email
	public Member getMemberByEmail(String email);

	// Retrieve all members
	public List<Member> getAllMembers();

	// Update an existing member
	public Member updateMember(Long memberId, Member member);

	// Delete a member by ID
	public void deleteMemberById(Long id);


	public LoginResponse getMemberProfile(String email);

}
