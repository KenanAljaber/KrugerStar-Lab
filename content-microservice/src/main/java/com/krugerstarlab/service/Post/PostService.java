package com.krugerstarlab.service.Post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.krugerstarlab.entity.Post;
import com.krugerstarlab.entity.comment.Comment;

public interface PostService {

	// Create a new Post
	public Post createPost(Post post);

	// Retrieve a Post by ID
	public Post getPostById(Long id);

	// Retrieve all Posts
	public List<Post> getAllPosts();

	// Update an existing Post
	public Post updatePost(Long postId, Post post);

	// Delete a Post by ID
	public void deletePostById(Long id);
	
	 List<Post> findAllByClass(@Param("clazz") Class<?> clazz);
	 
	 Optional<Post> findByIdAndClass(@Param("id") Long id, @Param("classType") Class<?> classType);
	 
	 Post addComment (Comment comment,Long postId);
	 
	 




}
