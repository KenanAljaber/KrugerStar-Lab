package com.krugerstarlab.service.project_submission;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.project.ProjectPost;
import com.krugerstarlab.entity.project.ProjectSubmission;
import com.krugerstarlab.repository.ProjectPostRepository;
import com.krugerstarlab.repository.ProjectSubmissionRepository;

@Service
public class ProjectSubmissionServiceImpl implements ProjectSubmissionService {

	private final ProjectSubmissionRepository projectSubmissionRepository;
	
	private final ProjectPostRepository projectPostRepo;

	private static final Logger logger = LoggerFactory.getLogger(ProjectSubmissionServiceImpl.class);

	public ProjectSubmissionServiceImpl(ProjectSubmissionRepository ProjectSubmissionRepository,ProjectPostRepository projectPostRepo) {
		this.projectSubmissionRepository = ProjectSubmissionRepository;
		this.projectPostRepo=projectPostRepo;

	}

	@Override
	public ProjectSubmission getProjectSubmissionById(Long submissionId) {
		return projectSubmissionRepository.findById(submissionId).orElse(null);
	}



	@Override
	public ProjectSubmission updateProjectSubmission(Long ProjectSubmissionId, ProjectSubmission submission) {
		ProjectSubmission existingProjectSubmission = getProjectSubmissionById(ProjectSubmissionId);
		if (existingProjectSubmission == null) {
			return null;
		}
		 existingProjectSubmission.setSubmissionTitle(submission.getSubmissionTitle());
		 existingProjectSubmission.setNote(submission.getNote());

		return projectSubmissionRepository.save(existingProjectSubmission);
	}

	@Override
	public boolean deleteProjectSubmissionById(Long submissionId) {
		try {
			projectSubmissionRepository.deleteById(submissionId);
			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	@Override
	public List<ProjectSubmission> getAllBySubmitterId(Long id) {
		List<ProjectSubmission> submissions = projectSubmissionRepository.getAllBySubmitterId(id);
		return submissions;
	}

}
