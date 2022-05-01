package com.todaymeal.todaymeal.domain.comment;

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
public class CommentLike {
	protected CommentLike() {}

	@Builder(builderMethodName = "commentLikeBuilder")
	public CommentLike(User user, Comment comment) {
		setUser(user);
		setComment(comment);
		liked_date = LocalDateTime.now();
	}

	@Id
	@GeneratedValue
	@Column(name = "comment_like_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	private LocalDateTime liked_date;

	public void setUser(User user) {
		this.user = user;
		user.getCommentLikes().add(this);
	}

	public void setComment(Comment comment) {
		this.comment = comment;
		comment.getCommentLikes().add(this);
	}
}
