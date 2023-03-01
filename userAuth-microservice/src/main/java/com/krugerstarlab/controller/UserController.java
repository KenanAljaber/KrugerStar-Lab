package com.krugerstarlab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.dto.UserProfile;
import com.krugerstarlab.entity.User;
import com.krugerstarlab.service.user.UserService;

@RequestMapping("api/v1/users/")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("/{id}")
	public ResponseEntity<UserProfile> getMemberById(@PathVariable Long id) {
		UserProfile member = userService.getUserById(id);
		if (member != null) {
			return new ResponseEntity<>(member, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	
}
