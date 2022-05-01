package com.todaymeal.todaymeal.web.dto.responseDto;

import com.todaymeal.todaymeal.domain.post.Post;

import lombok.Data;

@Data
public class PostDto {
	private Long postId;
	private Long writerId;
	private String restaurantName;
	private String image;
	private Integer likeCnt;
	private String text;

	public PostDto(Post post) {
		this.postId = post.getId();
		this.writerId = post.getUser().getId();
		this.restaurantName = post.getRestaurant().getRestaurantName();
		this.image = new String(post.getEncodedImage());
		this.text = post.getText();
		this.likeCnt = post.getLikeCount();
	}
}
