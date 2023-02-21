package com.krugerstarlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krugerstarlab.entity.Post;


public interface PostRepository extends JpaRepository<Post,Long> {
	
	@Query("SELECT p FROM Post p WHERE TYPE(p) = :clazz")
    List<Post> findAllByClass(@Param("clazz") Class<?> clazz);
	
	@Query("SELECT p FROM Post p WHERE p.id = :id AND TYPE(p) = :classType")
    Optional<Post> findByIdAndClass(@Param("id") Long id, @Param("classType") Class<?> classType);
	
	List<Post> findByUserId (Long userId);

}
