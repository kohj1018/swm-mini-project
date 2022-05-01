package com.todaymeal.todaymeal.domain.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.todaymeal.todaymeal.domain.BaseEntity;
import com.todaymeal.todaymeal.domain.comment.Comment;
import com.todaymeal.todaymeal.domain.restaurant.Restaurant;

import org.springframework.util.Assert;

import com.todaymeal.todaymeal.domain.user.User;
import com.todaymeal.todaymeal.global.constants.ErrorMessages;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Post extends BaseEntity {
	protected Post() {}

	@Builder(builderMethodName = "postBuilder")
	public Post(Restaurant restaurant, String text, String encodedImage) {
		setRestaurant(restaurant);
		this.text = text;
		this.encodedImage = encodedImage.getBytes();
	}

	@Id
	@GeneratedValue
	@Column(name = "post_id")
	private Long id;

	private Integer star;
	private String text;

	@Column(columnDefinition="BLOB")
	private byte[] encodedImage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@OneToMany(mappedBy = "post", orphanRemoval = true)
	private List<PostLike> postLikes = new ArrayList<>();

	@OneToMany(mappedBy = "post", orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public void setUser(User user) {
		this.user = user;
		user.getPosts().add(this);
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
		restaurant.getPosts().add(this);
	}

	public void addPostLikes(PostLike postLike) {
		postLike.setPost(this);
	}

	public void addComments(Comment comment) {
		comment.setPost(this);
	}

	public void setStar(Integer star) {
		Assert.isTrue(star < 0 || star > 5, ErrorMessages.RESTAURANT_STAR_RANGE_ERROR);
		this.star = star;
	}

	public Integer getLikeCount() {
		return postLikes.size();
	}

	public Boolean isLiked(User user) {
		return postLikes.stream()
				.map(PostLike::getUser)
				.anyMatch(likedUser -> likedUser.getId().equals(user.getId()));
	}
}
