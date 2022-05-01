package com.todaymeal.todaymeal.web.dto.responseDto;

import com.todaymeal.todaymeal.global.dto.DtoMetaData;

import lombok.Data;

@Data
public class CreatePostResponseDto extends BaseResponseDto{
	private Long postId;

	public CreatePostResponseDto(DtoMetaData dtoMetaData, Long postId) {
		this.dtoMetaData = dtoMetaData;
		this.postId = postId;
	}

	public CreatePostResponseDto(DtoMetaData dtoMetaData) {
		this.dtoMetaData = dtoMetaData;
		this.postId = null;
	}
}
