package com.krugerstarlab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krugerstarlab.entity.project.ProjectSubmission;



public interface ProjectSubmissionRepository extends JpaRepository<ProjectSubmission,Long> {
	
	List<ProjectSubmission> getAllBySubmitterId(Long submitterId);


}
