package com.krugerstarlab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.security_model.UserDetailsRole;
import com.krugerstarlab.exception.InvalidUsernameOrPasswordException;
import com.krugerstarlab.service.authentication.AuthenticationService;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

@RestController
@RequestMapping("api/v1/users/members")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;

	
	@PostMapping
	public ResponseEntity<Member> createMember(@RequestBody Member member) {
		Member createdMember = memberService.createMember(member);
		return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
		Member member = memberService.getMemberById(id);
		if (member != null) {
			return new ResponseEntity<>(member, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
		Member member = memberService.getMemberByEmail(email);
		if (member != null) {
			return new ResponseEntity<>(member, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<Member>> getAllMembers() {
		logger.debug("[+] Getting all members from database");
		List<Member> members = memberService.getAllMembers();
		return new ResponseEntity<>(members, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
		Member updatedMember = memberService.updateMember(id, member);
		if (updatedMember != null) {
			return new ResponseEntity<>(updatedMember, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMemberById(@PathVariable Long id) {
		memberService.deleteMemberById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
