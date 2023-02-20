package com.krugerstarlab;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.krugerstarlab.entity.member.Group;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.member.Role;
import com.krugerstarlab.entity.tutor.Tutor;
import com.krugerstarlab.entity.tutor.TutorType;
import com.krugerstarlab.service.group.GroupService;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthMicroserviceApplication {

	@Autowired
	private MemberService service;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private TutorService tutorService;
	

	public static void main(String[] args) {
		SpringApplication.run(UserAuthMicroserviceApplication.class, args);
	}
	
	@PostConstruct
	public void injectData () {
		//Dummy data to do some tests
	Member member=new Member("hayan", "aljaber", "test@test.com","0999723294"
			,"12345678","photo", Role.ROLE_BACKEND_DEV);
	Member member2=new Member("kenan", "aljaber", "keno12333@hotmail.com","0999723294"
			,"12345678","photo", Role.ROLE_FULL_STACK);
	
	Group g=new Group("VengaCoders");
	
	Tutor t=new Tutor("kenan", "aljaber", "tutor@test.com", "0999723294", "12345678","photo"
			,TutorType.FRONTEND);
	tutorService.createTutor(t);
	
	g.addMember(member);
	g.addMember(member2);
	groupService.createGroup(g);
	
	service.createMember(member);
	service.createMember(member2);
	
	}
	

}
