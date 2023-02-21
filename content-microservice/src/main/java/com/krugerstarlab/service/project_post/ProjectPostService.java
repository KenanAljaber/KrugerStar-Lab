package com.krugerstarlab.service.project_post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.krugerstarlab.entity.Post;
import com.krugerstarlab.entity.ProjectPost;

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
	





}
