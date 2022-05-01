package com.todaymeal.todaymeal.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.todaymeal.todaymeal.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class PostLike {
	protected PostLike() {}

	@Builder(builderMethodName = "postLikeBuilder")
	public PostLike(User user, Post post) {
		setUser(user);
		setPost(post);
		this.liked_date = LocalDateTime.now();
	}

	@Id
	@GeneratedValue
	@Column(name = "like_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	private LocalDateTime liked_date;

	public void setUser(User user) {
		this.user = user;
		user.getPostLikes().add(this);
	}

	public void setPost(Post post) {
		this.post = post;
		post.getPostLikes().add(this);
	}
}
