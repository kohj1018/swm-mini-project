package com.todaymeal.todaymeal.web.dto.responseDto;

import com.todaymeal.todaymeal.global.dto.DtoMetaData;

import lombok.Data;

@Data
public class CreateCommentResponseDto extends BaseResponseDto{
	private Long commentId;

	public CreateCommentResponseDto(DtoMetaData dtoMetaData) {
		this.dtoMetaData = dtoMetaData;
		commentId = null;
	}

	public CreateCommentResponseDto(DtoMetaData dtoMetaData, Long commentId) {
		this.dtoMetaData = dtoMetaData;
		this.commentId = commentId;
	}
}
