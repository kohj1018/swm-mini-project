package com.todaymeal.todaymeal.web.dto.responseDto;

import java.util.ArrayList;
import java.util.List;

import com.todaymeal.todaymeal.domain.comment.Comment;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CommentDto {
	private String username;
	private Long userId;
	private String description;
	private Long commentId;
	private List<ChildCommentDto> child = new ArrayList<>();

	public CommentDto(Comment comment) {
		username = comment.getUser().getNickName();
		userId = comment.getUser().getId();
		description = comment.getText();
		commentId = comment.getId();
	}
}
