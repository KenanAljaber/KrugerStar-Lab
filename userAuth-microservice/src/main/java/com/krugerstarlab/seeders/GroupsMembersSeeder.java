package com.krugerstarlab.seeders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.krugerstarlab.entity.member.Group;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.member.Role;
import com.krugerstarlab.service.group.GroupService;
import com.krugerstarlab.service.member.MemberService;
import com.krugerstarlab.service.tutor.TutorService;

@Component
public class GroupsMembersSeeder {
	private File namesFile = new File("src/main/resources/static/names.txt");
	private File groupsfile = new File("src/main/resources/static/groups.txt");

	@Autowired
	private MemberService service;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private TutorService tutorService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void generateGroups(int groupNum) {
		Member member=new Member("Kenan", "Aljaber", "Keno12333@hotmail.com","0999723294"
				,passwordEncoder.encode("12345678"),"photo", Role.FULL_STACK);
		Member member2=new Member("Kevin", "Mantilla", "kevin-m@hotmail.com","0999734234"
				,passwordEncoder.encode("12345678"),"photo", Role.FULL_STACK);
		Member member3=new Member("Jose", "Gavilanes", "jose-gavilanes@hotmail.com","0987634587"
				,passwordEncoder.encode("12345678"),"photo", Role.FULL_STACK);
		
		Group g=new Group("VengaCoders");
		
		
		g.addMember(member);
		g.addMember(member2);
		g.addMember(member3);
		groupService.createGroup(g);
		
		
		try {
			List<Group> groups=MemberGenerator.groupsGenerator(passwordEncoder,groupsfile, groupNum);
			groups.forEach(group-> groupService.createGroup(group));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
