package com.krugerstarlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.krugerstarlab.entity.Member;
import com.krugerstarlab.entity.Role;
import com.krugerstarlab.service.member.MemberService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthMicroserviceApplication {

	@Autowired
	private MemberService service;
	

	public static void main(String[] args) {
		SpringApplication.run(UserAuthMicroserviceApplication.class, args);
	}
	
	@PostConstruct
	public void injectData () {
	Member member=new Member("kenan", "aljaber", "keno12333@hotmail.com","0999723294"
			,"12345678","photo", Role.ROLE_FULL_STACK);
	service.createMember(member);
	
	}
	

}
