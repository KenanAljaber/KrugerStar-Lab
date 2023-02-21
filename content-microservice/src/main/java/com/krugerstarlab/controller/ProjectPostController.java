package com.krugerstarlab.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.entity.project.ProjectPost;
import com.krugerstarlab.entity.project.ProjectSubmission;
import com.krugerstarlab.service.project_post.ProjectPostService;

import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/content/project-posts")
@Validated
public class ProjectPostController {

    private final ProjectPostService projectPostService;


    public ProjectPostController(ProjectPostService projectPostService) {
        this.projectPostService = projectPostService;
    }

    @GetMapping
    public List<ProjectPost> getAllProjectPosts() {
        return projectPostService.getAllProjectPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectPost> getProjectPostById(@PathVariable("id") Long id) {
        ProjectPost projectPost = projectPostService.getProjectPostById(id);
        if (projectPost == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectPost);
    }

    @PostMapping
    public ResponseEntity<ProjectPost> createProjectPost(@Valid @RequestBody ProjectPost projectPost) {
        ProjectPost createdProjectPost = projectPostService.createProjectPost(projectPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectPost> updateProjectPost(@PathVariable("id") Long id, @Valid @RequestBody ProjectPost projectPost) {
        ProjectPost updatedProjectPost = projectPostService.updateProjectPost(id, projectPost);
        if (updatedProjectPost == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProjectPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectPostById(@PathVariable("id") Long id) {
        projectPostService.deleteProjectPostById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/submission/{projectId}")
    public ResponseEntity<Void> addSubmission(@PathVariable("projectId") Long projectId,@RequestBody ProjectSubmission submission) {
      try {  boolean added=projectPostService.addSubmission(projectId, submission);
        
        return added==true? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();}
      catch (Exception e) {
    	  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

}
