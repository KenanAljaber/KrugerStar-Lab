package com.krugerstarlab.dto;

import com.krugerstarlab.entity.member.Role;
import com.krugerstarlab.entity.tutor.TutorType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {


    private Long id;


    private String firstName;


    private String lastName;

    private String email;

 
    private String phoneNumber;


    private String photo;

 
    private String githubLink;


    private String linkedinLink;
    
    private Role role;
    
    private TutorType type;
   
	
}
