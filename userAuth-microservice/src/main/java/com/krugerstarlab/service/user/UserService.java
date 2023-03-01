package com.krugerstarlab.service.user;

import org.springframework.stereotype.Service;

import com.krugerstarlab.dto.UserProfile;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.tutor.Tutor;
import com.krugerstarlab.repository.MemberRepository;
import com.krugerstarlab.repository.TutorRepository;

@Service
public class UserService {

	private final MemberRepository memberRepo;
	
	private final TutorRepository tutorRepo;

	public UserService(MemberRepository memberRepo, TutorRepository tutorRepo) {
		this.memberRepo = memberRepo;
		this.tutorRepo = tutorRepo;
	}
	
	
	public UserProfile getUserById (Long id) {
		Member member= memberRepo.findById(id).orElse(null);

		
		if(member!=null) {
			member.getMyGroup();
			return  UserProfile.builder()
					.id(member.getId()).firstName(member.getFirstName())
					.lastName(member.getLastName()).email(member.getEmail())
					.phoneNumber(member.getPhoneNumber()).phoneNumber(member.getPhoto())
					.linkedinLink(member.getLinkedinLink()).githubLink(member.getGithubLink())
					.role(member.getRole()).myGroup(member.getMyGroup()).build();
		}
		Tutor tutor=tutorRepo.findById(id).orElse(null) ;
		return UserProfile.builder()
				.id(tutor.getId()).firstName(tutor.getFirstName())
				.lastName(tutor.getLastName()).email(tutor.getEmail())
				.phoneNumber(tutor.getPhoneNumber()).phoneNumber(tutor.getPhoto())
				.linkedinLink(tutor.getLinkedinLink()).githubLink(tutor.getGithubLink())
				.type(tutor.getType()).build();
	}
	

	
}
