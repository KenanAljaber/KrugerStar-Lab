package com.krugerstarlab.serviceUserDetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.dto.TokenDto;
import com.krugerstarlab.entity.Member;
import com.krugerstarlab.entity.Tutor;
import com.krugerstarlab.entity.User;
import com.krugerstarlab.entity.security_model.SecurityUser;
import com.krugerstarlab.entity.security_model.UserDetailsRole;
import com.krugerstarlab.security.JWTStore;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Lazy
	private final MemberService memeberService;

	@Lazy
	private final TutorService tutorService;


	
	public UserDetailsImpl(MemberService memberService, TutorService tutorService) {
		this.memeberService = memberService;
		this.tutorService = tutorService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SecurityUser user;
	    if (memeberService.getMemberByEmail(username) == null) {
	        if (tutorService.getTutorByEmail(username) == null) {
	            throw new UsernameNotFoundException("User not found with email: " + username);
	        } else {
	            Tutor tutor = tutorService.getTutorByEmail(username);
	            user=new SecurityUser(tutor.getId(), tutor.getEmail(), tutor.getPassword(), UserDetailsRole.TUTOR);
	            return user;
	        }
	    } else {
	    	Member member= memeberService.getMemberByEmail(username);
	    	user=new SecurityUser(member.getId(), member.getEmail(), member.getPassword(), UserDetailsRole.MEMBER);
	        return user;
	    }
	}
	
	
	

	public TokenDto validate(String token, String role) {
		if (!JWTStore.validateToken(token)) {
			return null;
		}
		String email = JWTStore.getEmailFromToken(token);
		if (email == null) {
			return null;
		}
		if (role.equals("TUTOR")) {
			return tutorService.getTutorByEmail(email) != null ? new TokenDto(token) : null;
		}
		return memeberService.getMemberByEmail(email) != null ? new TokenDto(token) : null;
	}

}
