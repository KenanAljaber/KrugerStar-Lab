package com.krugerstarlab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krugerstarlab.entity.project.ProjectPost;


public interface ProjectPostRepository extends JpaRepository<ProjectPost,Long> {
	
	List<ProjectPost> findByUserId (Long userId);

}
