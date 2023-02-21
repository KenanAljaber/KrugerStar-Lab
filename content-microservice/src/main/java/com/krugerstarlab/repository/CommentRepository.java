package com.krugerstarlab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krugerstarlab.entity.comment.Comment;



public interface CommentRepository extends JpaRepository<Comment,Long> {


}
