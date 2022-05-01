package com.todaymeal.todaymeal.web.dto.responseDto;

import com.todaymeal.todaymeal.domain.comment.Comment;

import lombok.Data;

@Data
public class ChildCommentDto {
	private Long postId;
	private String userName;
	private Long userId;
	private String description;
	private Long commentId;

	public ChildCommentDto(Comment comment) {
		postId = comment.getPost().getId();
		userId = comment.getUser().getId();
		userName = comment.getUser().getNickName();
		description = comment.getText();
		commentId = comment.getId();
	}
}
