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
import com.krugerstarlab.entity.User;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.security_model.SecurityUser;
import com.krugerstarlab.entity.security_model.UserDetailsRole;
import com.krugerstarlab.entity.tutor.Tutor;
import com.krugerstarlab.security.JWTStore;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Lazy
	private final MemberService memeberService;

	@Lazy
	private final TutorService tutorService;

	private JWTStore jwtStore;
	
	public UserDetailsImpl(MemberService memberService, TutorService tutorService,JWTStore jwtStore) {
		this.memeberService = memberService;
		this.tutorService = tutorService;
		this.jwtStore=jwtStore;
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
		if (!jwtStore.validateToken(token)) {
			return null;
		}
		String email = jwtStore.getEmailFromToken(token);
		if (email == null) {
			return null;
		}
		if (role.equals("TUTOR")) {
			return tutorService.getTutorByEmail(email) != null ? new TokenDto(token) : null;
		}
		return memeberService.getMemberByEmail(email) != null ? new TokenDto(token) : null;
	}

}
