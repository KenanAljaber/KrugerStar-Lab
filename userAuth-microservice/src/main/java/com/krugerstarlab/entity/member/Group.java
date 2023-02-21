package com.krugerstarlab.entity.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "member_group")
public class Group  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	//TODO add photo or avatar to the group
	
	public Group(String name) {
		this.name=name;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "myGroup")
	private List<Member> members;
	
	public boolean addMember(Member member) {
	    if (members == null) {
	        members = new ArrayList<>();
	    }
	    if (!members.contains(member)) {
	        member.setMyGroup(this);
	        members.add(member);
	        return true;
	    }
	    return false;
	}
	
}
