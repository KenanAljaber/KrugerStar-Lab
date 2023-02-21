package com.krugerstarlab.service.project_submission;

import java.util.List;

import com.krugerstarlab.entity.project.ProjectSubmission;

public interface ProjectSubmissionService {



	// Retrieve a ProjectSubmission by ID
	public ProjectSubmission getProjectSubmissionById(Long id);

	// Update an existing ProjectSubmission
	public ProjectSubmission updateProjectSubmission(Long projectSubmissionId, ProjectSubmission projectSubmission);

	// Delete a ProjectSubmission by ID
	public boolean deleteProjectSubmissionById(Long id);
	
	//Get all submissions by submitter id
	public List<ProjectSubmission> getAllBySubmitterId(Long id);




}
