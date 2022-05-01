package com.todaymeal.todaymeal.service.comment;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymeal.todaymeal.domain.comment.Comment;
import com.todaymeal.todaymeal.domain.comment.CommentRepository;
import com.todaymeal.todaymeal.domain.post.Post;
import com.todaymeal.todaymeal.global.dto.DtoMetaData;
import com.todaymeal.todaymeal.service.post.PostService;
import com.todaymeal.todaymeal.service.user.UserService;
import com.todaymeal.todaymeal.web.dto.requestDto.CreateCommentRequestDto;
import com.todaymeal.todaymeal.web.dto.responseDto.ChildCommentDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CommentDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CommentListResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CreateCommentResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserService userService;
	private final PostService postService;
	private final EntityManager em;

	@Transactional
	public CreateCommentResponseDto saveParentComment(CreateCommentRequestDto createCommentRequestDto, Long postId) {
		DtoMetaData dtoMetaData;
		try {
			Comment comment = makeParentCommentEntity(createCommentRequestDto, postId);
			em.persist(comment);
			dtoMetaData = new DtoMetaData("댓글 저장 성공");
			return new CreateCommentResponseDto(dtoMetaData, comment.getId());
		} catch (Exception e) {
			dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
			return new CreateCommentResponseDto(dtoMetaData);
		}
	}

	@Transactional
	public CreateCommentResponseDto saveChildComment(CreateCommentRequestDto createCommentRequestDto, Long postId,
			Long parentId) {
		DtoMetaData dtoMetaData;
		try {
			Comment comment = makeChildCommentEntity(createCommentRequestDto, postId, parentId);
			em.persist(comment);

			dtoMetaData = new DtoMetaData("댓글 저장 성공");
			return new CreateCommentResponseDto(dtoMetaData, comment.getId());
		} catch (Exception e) {
			dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
			return new CreateCommentResponseDto(dtoMetaData);
		}
	}

	@Transactional
	public CommentListResponseDto findAllByPostId(Long postId) {
		DtoMetaData dtoMetaData;
		try {
			Post post = postService.findById(postId);
			List<Comment> findCommentList = commentRepository.findByPostOrderByCreateDate(post);
			CommentListResponseDto commentListResponseDto = makeFindCommentListToCommentListResponseDto(
					findCommentList);
			dtoMetaData = new DtoMetaData("댓글 조회에 성공했습니다.");
			commentListResponseDto.setDtoMetaData(dtoMetaData);
			return commentListResponseDto;
		} catch (Exception e) {
			dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
			return new CommentListResponseDto(dtoMetaData);
		}
	}

	private CommentListResponseDto makeFindCommentListToCommentListResponseDto(List<Comment> commentList) {
		CommentListResponseDto commentListResponseDto = new CommentListResponseDto();
		commentList.forEach(comment -> {
			if (comment.getParents() == null) {
				commentListResponseDto.getData().add(new CommentDto(comment));
			} else {
				commentListResponseDto.getData().stream()
						.filter(x -> x.getCommentId() == comment.getParents().getId())
						.findAny()
						.orElseThrow(IllegalArgumentException::new).
						getChild().add(new ChildCommentDto(comment));
			}
		});
		return commentListResponseDto;
	}

	public Comment findById(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new NullPointerException("존재하지 않는 댓글입니다."));
	}
	//builder패턴으로 처리하면 nullPointError 등장. 왜지

	private Comment makeParentCommentEntity(CreateCommentRequestDto createCommentRequestDto, Long postId) {
		return new Comment(userService.findCurrentAccountUser(), postService.findById(postId),
				createCommentRequestDto.getDescription());
	}

	private Comment makeChildCommentEntity(CreateCommentRequestDto createCommentRequestDto, Long postId, Long parentId) {
		return new Comment(userService.findCurrentAccountUser(), findById(parentId), postService.findById(postId),
				createCommentRequestDto.getDescription());
	}
}
