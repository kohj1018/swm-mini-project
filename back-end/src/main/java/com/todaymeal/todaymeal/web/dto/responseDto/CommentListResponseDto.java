package com.todaymeal.todaymeal.web.dto.responseDto;

import java.util.ArrayList;
import java.util.List;

import com.todaymeal.todaymeal.global.dto.DtoMetaData;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentListResponseDto extends BaseResponseDto {
	private List<CommentDto> data = new ArrayList<>();

	public CommentListResponseDto(DtoMetaData dtoMetaData) {
		this.dtoMetaData = dtoMetaData;
		data = null;
	}
}
