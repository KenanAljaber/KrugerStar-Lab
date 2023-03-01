package com.krugerstarlab;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.krugerstarlab.entity.member.Group;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.member.Role;
import com.krugerstarlab.entity.tutor.Tutor;
import com.krugerstarlab.entity.tutor.TutorType;
import com.krugerstarlab.seeders.GroupsMembersSeeder;
import com.krugerstarlab.seeders.MemberGenerator;
import com.krugerstarlab.service.group.GroupService;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthMicroserviceApplication {


	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private GroupsMembersSeeder seeder;
	

	public static void main(String[] args) {
		SpringApplication.run(UserAuthMicroserviceApplication.class, args);
	}
	
	@PostConstruct
	public void injectData () {

	
	Tutor t=new Tutor("Juan", "Sotomayor", "juanseb@kruger.com", "0999723294", "12345678","photo"
			,TutorType.FRONTEND);
	tutorService.createTutor(t);
	
	seeder.generateGroups(10);
	

	
	}
	

}
