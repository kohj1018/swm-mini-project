package com.todaymeal.todaymeal.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.todaymeal.todaymeal.web.dto.responseDto.BaseResponseDto;

@Component
public class ControllerHelper {

	public <T extends BaseResponseDto> ResponseEntity<T> makeResponseEntityByDto(T postDetailDto) {
		if (postDetailDto.getDtoMetaData().hasError()) {
			return ResponseEntity.ok().body(postDetailDto);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(postDetailDto);
	}
}
