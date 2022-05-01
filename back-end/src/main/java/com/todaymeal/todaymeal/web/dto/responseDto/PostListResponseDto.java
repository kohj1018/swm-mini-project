package com.todaymeal.todaymeal.web.dto.responseDto;

import java.util.ArrayList;
import java.util.List;

import com.todaymeal.todaymeal.global.dto.DtoMetaData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostListResponseDto extends BaseResponseDto {
	private List<PostDto> data = new ArrayList<>();

	public PostListResponseDto(DtoMetaData dtoMetaData) {
		this.dtoMetaData = dtoMetaData;
	}

	public PostListResponseDto(DtoMetaData dtoMetaData, List<PostDto> data) {
		this.dtoMetaData = dtoMetaData;
		this.data = data;
	}
}
