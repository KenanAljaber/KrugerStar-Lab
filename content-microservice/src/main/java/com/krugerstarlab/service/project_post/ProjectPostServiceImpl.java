package com.krugerstarlab.service.project_post;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.comment.Comment;
import com.krugerstarlab.entity.project.ProjectPost;
import com.krugerstarlab.entity.project.ProjectSubmission;
import com.krugerstarlab.repository.ProjectPostRepository;


@Service
public class ProjectPostServiceImpl implements ProjectPostService {

    private final ProjectPostRepository ProjectPostRepository;

	


	private static final Logger logger = LoggerFactory.getLogger(ProjectPostServiceImpl.class);
	
	public ProjectPostServiceImpl(ProjectPostRepository ProjectPostRepository ) {
		this.ProjectPostRepository = ProjectPostRepository;


	}
	
	
	

	@Override
	public List<ProjectPost> getAllProjectPosts() {
		return ProjectPostRepository.findAll();
	}

	@Override
	public ProjectPost getProjectPostById(Long projectPostId) {
		return ProjectPostRepository.findById(projectPostId).orElse(null);
	}

	@Override
	public ProjectPost createProjectPost(ProjectPost projectPost) {
		return ProjectPostRepository.save(projectPost);
	}

	@Override
	public ProjectPost updateProjectPost(Long projectPostId, ProjectPost projectPost) {
		ProjectPost existingProjectPost = getProjectPostById(projectPostId);
		if (existingProjectPost == null) {
			return null;
		}
		existingProjectPost.setContent(projectPost.getContent());
		existingProjectPost.setContent(projectPost.getContent());
		List<Comment> comments=new ArrayList<>();
		projectPost.getComments().forEach(it-> comments.add(it));
		existingProjectPost.setComments(comments);

		return ProjectPostRepository.save(existingProjectPost);
	}

	@Override
	public void deleteProjectPostById(Long projectPostId) {
		ProjectPostRepository.deleteById(projectPostId);
	}




	@Override
	public boolean addSubmission(Long projectId,ProjectSubmission submission) {
		System.out.println("this is service "+projectId);
		ProjectPost project= this.getProjectPostById(projectId);
		if(project==null) {
			return false;
		}
		boolean added =project.addSubmission(submission);
		this.updateProjectPost(projectId, project);
		return added;
		
	}

	




	


}
;