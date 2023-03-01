package com.krugerstarlab.entity.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.krugerstarlab.entity.Post;
import com.krugerstarlab.entity.PostType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("ProjectPost")
public  class ProjectPost extends Post  {

	@Id
	@GeneratedValue(generator  = "post_seq")
	@SequenceGenerator(name = "post_seq",sequenceName = "post_sequence")
	private Long id;
	
	@Column(nullable = false)
    private LocalDate deadline;
	
	@Enumerated(EnumType.STRING)
	private ProjectType porjectType;
	
	private final PostType type=PostType.PROJECT_POST;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectSubmission> submissions;

	public ProjectPost(LocalDate deadline,@NotNull Long userId, @NotBlank String content,ProjectType type) {
		super(userId,content);
		this.deadline = deadline;
		this.porjectType=type;
	}

	public boolean addSubmission(ProjectSubmission submission) {
		if(submissions==null) {
			submissions=new ArrayList<>();
		}
		if(submissions.stream()
				.noneMatch(it-> it.getSubmitterId()==submission.getSubmitterId())) {
			submission.setProject(this);
			submissions.add(submission);
			return true;
			
		}
		return false;
	}
	


}
