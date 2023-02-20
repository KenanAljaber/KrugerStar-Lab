package com.krugerstarlab.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.dto.LoginRequest;
import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.entity.Member;
import com.krugerstarlab.entity.security_model.SecurityUser;
import com.krugerstarlab.security.JWTStore;
import com.krugerstarlab.service.member.MemberService;

@RestController
@RequestMapping("api/v1/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private PasswordEncoder  passwordEncoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){
    	System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*------------********************** Controller");
    	try {
    	SecurityUser user= (SecurityUser) userDetailsService.loadUserByUsername(request.getEmail());
    	System.out.println(user);
    	LoginResponse response=memberService.login(user,request);
   	 Authentication authentication = authManager.authenticate(
             new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

     SecurityContextHolder.getContext().setAuthentication(authentication);
     
     Authentication test=SecurityContextHolder.getContext().getAuthentication();
     if(test!=null && test.isAuthenticated()) {
    	 System.out.println("yesyesyesyeyseyesyesyeyseyseyseyseyseyseysey go to sleeeeeeeeeeeeep");
     }else {
    	 System.out.println("oh shit here we go agian");
     }
    
    	 return ResponseEntity.ok(response);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.badRequest().build();
    	}
     
    

        
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        Member member = memberService.getMemberByEmail(email);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        Member updatedMember = memberService.updateMember(id, member);
        if (updatedMember != null) {
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable Long id) {
        memberService.deleteMemberById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
