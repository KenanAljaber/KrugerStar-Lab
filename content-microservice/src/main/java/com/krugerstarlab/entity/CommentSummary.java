package com.krugerstarlab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSummary {
	
	private String content;
	
	private Long userId;
	
	private Long commentId;
	
	private String createdAt;
	
}
