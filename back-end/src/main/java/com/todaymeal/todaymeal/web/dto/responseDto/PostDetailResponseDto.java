package com.todaymeal.todaymeal.web.dto.responseDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.todaymeal.todaymeal.domain.post.Post;
import com.todaymeal.todaymeal.domain.user.User;
import com.todaymeal.todaymeal.global.dto.DtoMetaData;

import lombok.Data;

@Data
public class PostDetailResponseDto extends BaseResponseDto{

	private String profileImageUrl;
	private String email;
	private String restaurantName;
	private String foodImage;
	private Integer likeCount;
	private Boolean isLiked;
	private String description;
	private Long userId;

	public PostDetailResponseDto(DtoMetaData dtoMetaData, Post post, User user) {
		this.dtoMetaData = dtoMetaData;
		this.profileImageUrl = post.getUser().getPicture();
		this.email = post.getUser().getEmail();
		this.restaurantName = post.getRestaurant().getRestaurantName();
		this.foodImage = new String(post.getEncodedImage());
		this.likeCount = post.getLikeCount();
		this.isLiked = post.isLiked(user);
		this.description = post.getText();
		this.userId = post.getUser().getId();
	}

	public PostDetailResponseDto(DtoMetaData dtoMetaData) {
		this.dtoMetaData = dtoMetaData;
		this.profileImageUrl = null;
		this.email = null;
		this.restaurantName = null;
		this.foodImage = null;
		this.likeCount = null;
		this.isLiked = null;
		this.description = null;
		this.userId = null;
	}
}
