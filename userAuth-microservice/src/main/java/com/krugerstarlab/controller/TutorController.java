package com.krugerstarlab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.krugerstarlab.entity.tutor.Tutor;
import com.krugerstarlab.exception.InvalidUsernameOrPasswordException;
import com.krugerstarlab.service.authentication.AuthenticationService;
import com.krugerstarlab.service.tutor.TutorService;

@RestController
@RequestMapping("api/v1/users/tutors")
public class TutorController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private TutorService tutorService;
	


	@PostMapping
	public ResponseEntity<Tutor> createTutor(@RequestBody Tutor tutor) {
		Tutor createdTutor = tutorService.createTutor(tutor);
		return new ResponseEntity<>(createdTutor, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tutor> getTutorById(@PathVariable Long id) {
		Tutor tutor = tutorService.getTutorById(id);
		if (tutor != null) {
			return new ResponseEntity<>(tutor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Tutor> getTutorByEmail(@PathVariable String email) {
		Tutor tutor = tutorService.getTutorByEmail(email);
		if (tutor != null) {
			return new ResponseEntity<>(tutor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<Tutor>> getAllTutors() {
		List<Tutor> tutors = tutorService.getAllTutors();
		return new ResponseEntity<>(tutors, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tutor> updateTutor(@PathVariable Long id, @RequestBody Tutor tutor) {
		Tutor updatedTutor = tutorService.updateTutor(id, tutor);
		if (updatedTutor != null) {
			return new ResponseEntity<>(updatedTutor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTutorById(@PathVariable Long id) {
		tutorService.deleteTutorById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
