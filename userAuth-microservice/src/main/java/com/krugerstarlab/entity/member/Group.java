package com.krugerstarlab.entity.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.krugerstarlab.dto.MemberDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "member_group")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Group implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	//TODO add photo or avatar to the group
	private String photo;

	
	
	
	
	public Group(String name) {
		this.name=name;
		membersSummary=new ArrayList<>();
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "myGroup",cascade = {CascadeType.MERGE,CascadeType.PERSIST
										,CascadeType.REFRESH,CascadeType.DETACH})
	private List<Member> members;
	
	@Transient
	private List<MemberDto> membersSummary;
	
	
	
	public boolean addMember(Member member) {
	    if (members == null) {
	        members = new ArrayList<>();
	    }
	    if (!members.contains(member)) {
	    	member.setEmail(member.getEmail().toLowerCase());
	        member.setMyGroup(this);
	        members.add(member);
	        return true;
	    }
	    return false;
	}


	public List<MemberDto> getMembersSummary(){
		this.membersSummary=new ArrayList<>();
		if(members!=null && members.size()>0) {
			members.forEach(it-> membersSummary.add(new MemberDto(it.getId(),it.getFirstName()
					,it.getLastName(),
					it.getPhoto(),it.getRole()))
					);
		}
		return membersSummary;
	}
	
	
}
