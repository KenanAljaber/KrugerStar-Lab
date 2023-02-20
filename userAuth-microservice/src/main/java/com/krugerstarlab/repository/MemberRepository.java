package com.krugerstarlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krugerstarlab.entity.member.Member;



public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Optional<Member> findByEmail(String email);
	

}
