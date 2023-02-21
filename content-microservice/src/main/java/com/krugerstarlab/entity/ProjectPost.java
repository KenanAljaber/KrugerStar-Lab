package com.krugerstarlab.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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

	
	
	@Column(nullable = false)
    private LocalDate deadline;
	
	private final PostType type=PostType.PROJECT_POST;

	public ProjectPost(LocalDate deadline,@NotNull Long userId, @NotBlank String content) {
		super(userId,content);
		this.deadline = deadline;
	}

	
	


}
