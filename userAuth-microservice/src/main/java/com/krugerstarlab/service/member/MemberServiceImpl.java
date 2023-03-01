package com.krugerstarlab.service.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krugerstarlab.controller.MemberController;
import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.dto.UserProfile;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.repository.MemberRepository;
import com.krugerstarlab.security.JWTStore;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

	private PasswordEncoder passwordEncoder;
	


	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;

	}

	@Override
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Override
	public Member getMemberById(Long memberId) {
		return memberRepository.findById(memberId).orElse(null);
	}

	@Override
	public Member createMember(Member member) {
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setEmail(member.getEmail().toLowerCase());
		return memberRepository.save(member);
	}

	@Override
	public Member updateMember(Long memberId, Member member) {
		Member existingMember = getMemberById(memberId);
		if (existingMember == null) {
			return null;
		}
		existingMember.setFirstName(member.getFirstName());
		existingMember.setLastName(member.getLastName());
		existingMember.setEmail(member.getEmail());
		existingMember.setPhoneNumber(member.getPhoneNumber());
		existingMember.setRole(member.getRole());
		existingMember.setMyGroup(member.getMyGroup());

		return memberRepository.save(existingMember);
	}

	@Override
	public void deleteMemberById(Long memberId) {
		memberRepository.deleteById(memberId);
	}

	@Override
	public Member getMemberByEmail(String email) {
		return memberRepository.findByEmail(email).orElse(null);
	}



	@Override
	public LoginResponse getMemberProfile(String email) {
		Member member = getMemberByEmail(email);
		UserProfile up = UserProfile.builder()
				.id(member.getId())
				.firstName(member.getFirstName())
				.lastName(member.getLastName())
				.email(member.getEmail())
				.githubLink(member.getGithubLink())
				.linkedinLink(member.getGithubLink())
				.phoneNumber(member.getPhoneNumber())
				.photo(member.getPhoto())
				.role(member.getRole()).build();
		return LoginResponse.builder().status(HttpStatus.OK)
				.userProfile(up).message("Success")
				.build();
	}
	


}
