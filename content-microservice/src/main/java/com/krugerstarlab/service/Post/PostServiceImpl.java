package com.krugerstarlab.service.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.Comment;
import com.krugerstarlab.entity.Post;
import com.krugerstarlab.entity.PostType;
import com.krugerstarlab.entity.ProjectPost;
import com.krugerstarlab.repository.PostRepository;
import com.krugerstarlab.repository.ProjectPostRepository;

import jakarta.ws.rs.NotFoundException;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    
    private final ProjectPostRepository pprepository;


	


	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	
	public PostServiceImpl(PostRepository postRepository,ProjectPostRepository pprepository ) {
		this.postRepository = postRepository;
		this.pprepository=pprepository;


	}
	
	@Override
	public List<Post> findAllByClass(Class<?> clazz) {
		
		return postRepository.findAllByClass(clazz);
	}
	
	 @Override
	    public Optional<Post> findByIdAndClass(Long postId, Class<?> postClass) {
	        return postRepository.findByIdAndClass(postId, postClass);
	    }

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}

	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post updatePost(Long postId, Post post) {
		Post existingPost = getPostById(postId);
		if (existingPost == null) {
			return null;
		}
		existingPost.setContent(post.getContent());
		List<Comment> comments=new ArrayList<>();
		post.getComments().forEach(it-> comments.add(it));
		existingPost.setComments(comments);

		return postRepository.save(existingPost);
	}

	@Override
	public void deletePostById(Long postId) {
		postRepository.deleteById(postId);
	}

	@Override
	public Post addComment(Comment comment, Long postId) {
		Post post=postRepository.findById(postId).orElseThrow( );
		comment.setPost(post);
		post.getComments().add(comment);
		if(post.getType().equals(PostType.NORMAL_POST)) {
			
			
			return postRepository.save(post);
		}else {

			return pprepository.save((ProjectPost)post);
		}
		
	}



	




	


}
