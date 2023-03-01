package com.krugerstarlab.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.krugerstarlab.entity.comment.Comment;
import com.krugerstarlab.entity.comment.CommentSummary;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "post_type")
public class Post  {
	
	@Id
	@GeneratedValue(generator  = "post_seq")
	@SequenceGenerator(name = "post_seq",sequenceName = "post_sequence")
	private Long id;
	
	
	//id of the author
	@NotNull
	private Long userId;
	
	//the post content
	@Column(length = 10000)
	@NotBlank
	private String content;
	
	//the date this post was created
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	private final PostType type=PostType.NORMAL_POST;
	
	@JsonIgnore
	@OneToMany(mappedBy = "post",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Comment> comments;
	
	@Transient
	private List<CommentSummary> listOfComments=new ArrayList<>();

	public Post(@NotNull Long userId, @NotBlank String content) {
		this.userId = userId;
		this.content = content;
		createdAt=new Date();
		if(comments==null) {
		comments=new ArrayList<>();
		}
	}
	
	public Post() {
		comments=new ArrayList<>();
	}
	
	public void addComment(Comment comment) {
		if(comments==null) {
			comments=new ArrayList<>();
		}
		comment.setPost(this);
		comments.add(comment);
	}
	

	public List<CommentSummary> getListOfComments(){
		if(comments!=null) {
			comments.forEach(it->
			listOfComments.add(new CommentSummary(it.getContent(),it.getUserId(),it.getId(),it.getCreatedAt().toString())));
		}
		return listOfComments;
		
	}
	
	
	
	
	

}
