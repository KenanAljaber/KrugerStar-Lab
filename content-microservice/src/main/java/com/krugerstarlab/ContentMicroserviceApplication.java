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
import com.krugerstarlab.seeders.PostsAndProjectsSeeder;
import com.krugerstarlab.service.Post.PostService;
import com.krugerstarlab.service.comment.CommentService;
import com.krugerstarlab.service.project_post.ProjectPostService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class ContentMicroserviceApplication {

	
	@Autowired
	private PostsAndProjectsSeeder seeder;
	
	public static void main(String[] args) {
		SpringApplication.run(ContentMicroserviceApplication.class, args);
	}
	
	@PostConstruct
	public void fakeData () {
		
		seeder.generatePosts();
		seeder.generateProjectPosts();
	}

}
