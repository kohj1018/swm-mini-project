package com.todaymeal.todaymeal.domain.comment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.todaymeal.todaymeal.domain.BaseEntity;
import com.todaymeal.todaymeal.domain.post.Post;
import com.todaymeal.todaymeal.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Comment extends BaseEntity {

	protected Comment() {}

	@Builder(builderMethodName = "childCommentBuilder")
	public Comment(User user, Comment parents, Post post, String text) {
		setUser(user);
		setParents(parents);
		setPost(post);
		this.text = text;
	}

	@Builder(builderMethodName = "parentCommentBuilder")
	public Comment(User user, Post post, String text) {
		setUser(user);
		setPost(post);
		this.text = text;
	}

	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	private String text;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parents_id")
	private Comment parents;

	@OneToMany(mappedBy = "comment", orphanRemoval = true)
	private List<CommentLike> commentLikes = new ArrayList<>();

	@OneToMany(mappedBy = "parents", orphanRemoval = true)
	private List<Comment> children = new ArrayList<>();

	public void setUser(User user) {
		this.user = user;
		user.getComments().add(this);
	}

	public void setPost(Post post) {
		this.post = post;
		post.getComments().add(this);
	}

	public void setParents(Comment parents) {
		this.parents = parents;
		parents.getChildren().add(this);
	}

	public void addCommentLikes(CommentLike commentLike) {
		commentLike.setComment(this);
	}

	public void addChildren(Comment child) {
		child.setParents(this);
	}
}
