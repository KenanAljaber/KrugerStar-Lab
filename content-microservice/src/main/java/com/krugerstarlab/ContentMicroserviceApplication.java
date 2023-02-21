package com.krugerstarlab;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.krugerstarlab.entity.Post;
import com.krugerstarlab.entity.comment.Comment;
import com.krugerstarlab.entity.project.ProjectPost;
import com.krugerstarlab.entity.project.ProjectSubmission;
import com.krugerstarlab.entity.project.ProjectType;
import com.krugerstarlab.service.Post.PostService;
import com.krugerstarlab.service.comment.CommentService;
import com.krugerstarlab.service.project_post.ProjectPostService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class ContentMicroserviceApplication {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ProjectPostService ppService;
	
	@Autowired
	private CommentService cService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ContentMicroserviceApplication.class, args);
	}
	
	@PostConstruct
	public void fakeData () {
		Post p=new Post(2L,"This is the first post");
		Post createdP= postService.createPost(p);
		
		ProjectSubmission submission=new ProjectSubmission(1L,"link","the first submission",
				"this is a description of the first submission");
		ProjectPost pp=new ProjectPost(LocalDate.now(),1L, "This is the first project post",ProjectType.INDIVIDUAL);
		pp.addSubmission(submission);
		ProjectPost ppCreated=ppService.createProjectPost(pp);
		
		Comment c=new Comment(1L,"This is the comment by user id 2",createdP.getId());
		c.setPost(p);
		p.getComments().add(c);
		postService.updatePost(p.getId(), p);
		
		Comment c2=new Comment(1L,"This is the comment by user id 11",ppCreated.getId());
		c2.setPost(ppCreated);
		ppCreated.getComments().add(c2);
		ppService.updateProjectPost(ppCreated.getId(), ppCreated);
		
		
		
	}

}
