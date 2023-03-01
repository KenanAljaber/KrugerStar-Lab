package com.krugerstarlab.entity.tutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.krugerstarlab.entity.User;
import com.krugerstarlab.entity.member.Role;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tutor extends User {
	
	@Id
	@GeneratedValue(generator  = "user_seq")
	@SequenceGenerator(name = "user_seq",sequenceName = "user_sequence")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TutorType type;
	
	
	public Tutor(String firstName, String lastName, 
			String email, String phoneNumber, String password, String photo,TutorType type) {
		super( firstName,  lastName,  email,  phoneNumber,  password,  photo);
		this.type=type;
		
	}
	
}
