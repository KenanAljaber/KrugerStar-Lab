package com.krugerstarlab.service.comment;

import java.util.List;

import com.krugerstarlab.entity.Comment;

public interface CommentService {

	// Create a new Comment
	public Comment createComment(Comment Comment);

	// Retrieve a Comment by ID
	public Comment getCommentById(Long id);

	// Update an existing Comment
	public Comment updateComment(Long CommentId, Comment Comment);

	// Delete a Comment by ID
	public boolean deleteCommentById(Long id);




}
