package com.todaymeal.todaymeal.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todaymeal.todaymeal.domain.post.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostOrderByCreateDate(Post post);
}
