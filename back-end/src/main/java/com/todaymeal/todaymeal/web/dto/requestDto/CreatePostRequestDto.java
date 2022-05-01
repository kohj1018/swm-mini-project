package com.todaymeal.todaymeal.web.dto.requestDto;

import lombok.Data;

@Data
public class CreatePostRequestDto {
	private Long userId;
	private String restaurantName;
	private String foodImage;
	private String description;
}
