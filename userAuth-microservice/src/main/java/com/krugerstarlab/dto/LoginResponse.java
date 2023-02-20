package com.krugerstarlab.dto;

import com.krugerstarlab.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {


    private Long id;


    private String firstName;


    private String lastName;

    private String email;

 
    private String phoneNumber;


    private String password;

    private String photo;

 
    private String githubLink;


    private String linkedinLink;
    
    private Role role;
    
    private String token;
    

    

	
}
