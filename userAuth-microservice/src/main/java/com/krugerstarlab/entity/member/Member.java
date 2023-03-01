package com.krugerstarlab.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.krugerstarlab.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class Member extends User  {
	
	@Id
	@GeneratedValue(generator  = "user_seq")
	@SequenceGenerator(name = "user_seq",sequenceName = "user_sequence")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	 private Group myGroup;

	public Member() {
		myGroup=new Group();
	}
	

	public Member(String firstName, String lastName, 
			String email, String phoneNumber, String password, String photo,Role role) {
		super( firstName,  lastName,  email,  phoneNumber,  password,  photo);
		this.role=role;
	}


	

	
	
}
