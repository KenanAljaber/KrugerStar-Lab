package com.krugerstarlab.controller;

import com.krugerstarlab.entity.Comment;
import com.krugerstarlab.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return comment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@Validated @RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @Validated @RequestBody Comment comment) {
        Comment updatedComment = commentService.updateComment(commentId, comment);
        return updatedComment == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long commentId) {
        boolean deleted=commentService.deleteCommentById(commentId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
