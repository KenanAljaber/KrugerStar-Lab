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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tutor extends User {
	
	@Enumerated(EnumType.STRING)
	private TutorType type;
	
	
	public Tutor(String firstName, String lastName, 
			String email, String phoneNumber, String password, String photo,TutorType type) {
		super( firstName,  lastName,  email,  phoneNumber,  password,  photo);
		this.type=type;
		
	}
	
}
