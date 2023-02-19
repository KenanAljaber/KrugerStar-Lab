package com.krugerstarlab.service.tutor;

import java.util.List;

import com.krugerstarlab.entity.Member;
import com.krugerstarlab.entity.Tutor;

public interface TutorService {

	// Create a new member
	public Tutor createTutor(Tutor tutor);

	// Retrieve a member by ID
	public Tutor getTutorById(Long id);

	// Retrieve a member by email
	public Tutor getTutorByEmail(String email);

	// Retrieve all members
	public List<Tutor> getAllTutors();

	// Update an existing member
	public Tutor updateTutor(Long tutorId, Tutor tutor);

	// Delete a member by ID
	public void deleteTutorById(Long id);

}
