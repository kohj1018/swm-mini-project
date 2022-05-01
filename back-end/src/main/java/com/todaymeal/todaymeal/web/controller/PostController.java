package com.todaymeal.todaymeal.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.todaymeal.todaymeal.service.post.PostService;
import com.todaymeal.todaymeal.web.dto.requestDto.CreatePostRequestDto;
import com.todaymeal.todaymeal.web.dto.responseDto.BaseResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CreatePostResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.PostDetailResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.PostListResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
	private final ControllerHelper controllerHelper;
	private final PostService postService;

	@GetMapping("/api/post/{postId}")
	public ResponseEntity<PostDetailResponseDto> postDetail(@PathVariable("postId") Long postId) {
		PostDetailResponseDto postDetailDto = postService.getPostDetailDto(postId);
		return controllerHelper.makeResponseEntityByDto(postDetailDto);
	}

	@PostMapping("/api/post")
	public ResponseEntity<CreatePostResponseDto> createPost(@RequestBody CreatePostRequestDto createPostRequestDto) {
		CreatePostResponseDto createPostResponseDto = postService.savePost(createPostRequestDto);
		return controllerHelper.makeResponseEntityByDto(createPostResponseDto);
	}


	@GetMapping("/api/post")
	public ResponseEntity<PostListResponseDto> listPost() {
		PostListResponseDto postListResponseDto = postService.findAllAsPostListResponseDto();
		return controllerHelper.makeResponseEntityByDto(postListResponseDto);
	}
}
