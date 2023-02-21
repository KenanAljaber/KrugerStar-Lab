package com.krugerstarlab.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "comments")
@AllArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long userId;

	@Column(length = 10000)
	@NotBlank
	private String content;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(insertable = false,updatable = false,nullable = false)
	private Post post;
	@Column(name="post_id")
	private Long postId;
	
	
	public Comment(@NotNull Long userId, @NotBlank String content, Long postId) {
		super();
		this.userId = userId;
		this.content = content;
		if(post!=null) {
			this.postId = post.getId();
		}else {
			this.postId=postId;
		}
		createdAt=new Date();
		
	}
	public Comment() {
		createdAt=new Date();
	}

	

}
