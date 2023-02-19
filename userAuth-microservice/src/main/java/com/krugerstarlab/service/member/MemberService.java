package com.krugerstarlab.service.member;

import java.util.List;

import com.krugerstarlab.entity.Member;

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

}
