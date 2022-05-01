package com.todaymeal.todaymeal.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todaymeal.todaymeal.service.comment.CommentService;
import com.todaymeal.todaymeal.web.dto.requestDto.CreateCommentRequestDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CommentListResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CreateCommentResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {

	private final ControllerHelper controllerHelper;
	private final CommentService commentService;

	@PostMapping("/api/post/{postId}/comment")
	public ResponseEntity<CreateCommentResponseDto> createComment(@PathVariable("postId") Long postId,
			@RequestBody CreateCommentRequestDto createCommentRequestDto) {
		CreateCommentResponseDto createCommentResponseDto = commentService.saveParentComment(createCommentRequestDto,
				postId);
		return controllerHelper.makeResponseEntityByDto(createCommentResponseDto);
	}

	@PostMapping("/api/post/{postId}/comment/{commentId}")
	public ResponseEntity<CreateCommentResponseDto> createChildComment(@PathVariable Long commentId,
			@PathVariable Long postId,
			@RequestBody CreateCommentRequestDto createCommentRequestDto) {
		CreateCommentResponseDto createCommentResponseDto = commentService.saveChildComment(createCommentRequestDto,
				postId, commentId);
		return controllerHelper.makeResponseEntityByDto(createCommentResponseDto);
	}

	@GetMapping("/api/post/{postId}/comment")
	public ResponseEntity<CommentListResponseDto> listComment(@PathVariable Long postId) {
		CommentListResponseDto commentListResponseDto = commentService.findAllByPostId(postId);
		return controllerHelper.makeResponseEntityByDto(commentListResponseDto);
	}
}
