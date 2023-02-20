package com.krugerstarlab.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	//TODO add photo or avatar to the group
	
	@OneToMany(mappedBy = "group")
	private List<Member> members;
	
	public boolean addMember(Member member) {
	    if (members == null) {
	        members = new ArrayList<>();
	    }
	    if (!members.contains(member)) {
	        member.setGroup(this);
	        members.add(member);
	        return true;
	    }
	    return false;
	}
	
}
