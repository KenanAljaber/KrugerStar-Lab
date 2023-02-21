package com.krugerstarlab.entity.project;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_submissions")
public class ProjectSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectPost project;

    @Enumerated(EnumType.STRING)
    private ProjectType submitterType;

    @Column(name = "submitter_id")
    private Long submitterId;

    private String submissionLink;

    private String submissionTitle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createdAt;

    private String note;

	public ProjectSubmission(Long submitterId, String submissionLink, String submissionTitle, String note) {
		this.submitterId = submitterId;
		this.submissionLink = submissionLink;
		this.submissionTitle = submissionTitle;
		this.note = note;
		if(project!=null) {
		this.submitterType=project.getPorjectType();
		}else {
			
		}
		createdAt=new Date();
	}
	
	public void setProject (ProjectPost project) {
		this.project=project;
		this.submitterType=project.getPorjectType();
	}
    
    
}
