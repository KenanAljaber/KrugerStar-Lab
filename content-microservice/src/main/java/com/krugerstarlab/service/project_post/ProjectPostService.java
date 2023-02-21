package com.krugerstarlab.service.project_post;

import java.util.List;

import com.krugerstarlab.entity.project.ProjectPost;
import com.krugerstarlab.entity.project.ProjectSubmission;

public interface ProjectPostService {

	// Create a new ProjectPost
	public ProjectPost createProjectPost(ProjectPost projectPost);

	// Retrieve a ProjectPost by ID
	public ProjectPost getProjectPostById(Long id);

	// Retrieve all ProjectPosts
	public List<ProjectPost> getAllProjectPosts();

	// Update an existing ProjectPost
	public ProjectPost updateProjectPost(Long projectPostId, ProjectPost projectPost);

	// Delete a ProjectPost by ID
	public void deleteProjectPostById(Long id);
	
	public boolean addSubmission (Long projectId,ProjectSubmission submission);
	





}
