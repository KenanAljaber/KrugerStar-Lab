package com.krugerstarlab.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krugerstarlab.entity.project.ProjectSubmission;
import com.krugerstarlab.service.project_submission.ProjectSubmissionService;

@RestController
@RequestMapping("api/v1/content/project-submissions")
public class ProjectSubmissionController {

    private final ProjectSubmissionService projectSubmissionService;

    public ProjectSubmissionController(ProjectSubmissionService projectSubmissionService) {
        this.projectSubmissionService = projectSubmissionService;
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<ProjectSubmission> getProjectSubmissionById(@PathVariable Long submissionId) {
        ProjectSubmission submission = projectSubmissionService.getProjectSubmissionById(submissionId);
        if (submission == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(submission);
        }
    }


    @PutMapping("/{submissionId}")
    public ResponseEntity<ProjectSubmission> updateProjectSubmission(@PathVariable Long submissionId, @RequestBody ProjectSubmission submission) {
        ProjectSubmission updatedSubmission = projectSubmissionService.updateProjectSubmission(submissionId, submission);
        if (updatedSubmission == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedSubmission);
        }
    }

    @DeleteMapping("/{submissionId}")
    public ResponseEntity<Void> deleteProjectSubmissionById(@PathVariable Long submissionId) {
        boolean isDeleted = projectSubmissionService.deleteProjectSubmissionById(submissionId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-submitter/{id}")
    public ResponseEntity<List<ProjectSubmission>> getAllBySubmitterId(@PathVariable Long id) {
        List<ProjectSubmission> submissions = projectSubmissionService.getAllBySubmitterId(id);
        if (submissions.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(submissions);
        }
    }
}
