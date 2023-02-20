package com.krugerstarlab.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.entity.Member;
import com.krugerstarlab.entity.security_model.SecurityUser;
import com.krugerstarlab.repository.MemberRepository;
import com.krugerstarlab.security.JWTStore;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    

    private PasswordEncoder  passwordEncoder;
    
    

    public MemberServiceImpl(MemberRepository memberRepository,PasswordEncoder  passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElse(null);
    }

    @Override
    public Member createMember(Member member) {
    	member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long memberId, Member member) {
        Member existingMember = getMemberById(memberId);
        if(existingMember==null) {
        	return null;
        }
        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setEmail(member.getEmail());
        existingMember.setPhoneNumber(member.getPhoneNumber());
        existingMember.setRole(member.getRole());
        existingMember.setGroup(member.getGroup());

        return memberRepository.save(existingMember);
    }

    @Override
    public void deleteMemberById(Long memberId) {
        memberRepository.deleteById(memberId);
    }

	@Override
	public Member getMemberByEmail(String email) {
		 return memberRepository.findByEmail(email)
	                .orElse(null);
	}

	@Override
	public LoginResponse login(SecurityUser user,LoginRequest request) {
		
		
		if(passwordEncoder.matches(request.getPassword(),user.getPassword())) {
		Member member=getMemberByEmail(user.getEmail());
		return LoginResponse.builder().id(member.getId())
		.firstName(member.getFirstName())
		.lastName(member.getLastName())
		.password(member.getPassword())
		.email(member.getEmail())
		.githubLink(member.getGithubLink())
		.linkedinLink(member.getGithubLink())
		.phoneNumber(member.getPhoneNumber())
		.photo(member.getPhoto())
		.role(member.getRole())
		.token(JWTStore.generateToken(member))
		.build();
		
		}
		
		throw new BadCredentialsException("Invalid username or password");

	}

	


}
