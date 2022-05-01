package com.todaymeal.todaymeal.service.post;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymeal.todaymeal.domain.post.Post;
import com.todaymeal.todaymeal.domain.post.PostRepository;
import com.todaymeal.todaymeal.domain.restaurant.Restaurant;
import com.todaymeal.todaymeal.domain.user.User;
import com.todaymeal.todaymeal.global.constants.ErrorMessages;
import com.todaymeal.todaymeal.global.dto.DtoMetaData;
import com.todaymeal.todaymeal.service.restaurant.RestaurantService;
import com.todaymeal.todaymeal.service.user.UserService;
import com.todaymeal.todaymeal.web.dto.requestDto.CreatePostRequestDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CommentListResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.CreatePostResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.PostDetailResponseDto;
import com.todaymeal.todaymeal.web.dto.responseDto.PostDto;
import com.todaymeal.todaymeal.web.dto.responseDto.PostListResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
	private final PostRepository postRepository;
	private final UserService userService;
	private final EntityManager em;
	private final RestaurantService restaurantService;

	public PostDetailResponseDto getPostDetailDto(Long postId) {
		DtoMetaData dtoMetaData;
		try {
			User currentAccountUser = userService.findCurrentAccountUser();
			Post post = findById(postId);
			dtoMetaData = new DtoMetaData("게시물 정보 가져오기 성공");
			return new PostDetailResponseDto(dtoMetaData, post, currentAccountUser);
		} catch (Exception e) {
			dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
			return new PostDetailResponseDto(dtoMetaData);
		}
	}

	@Transactional
	public CreatePostResponseDto savePost(CreatePostRequestDto createPostRequestDto) {
		DtoMetaData dtoMetaData;
		try {
			Post post = makePostEntity(createPostRequestDto);
			User user = userService.findById(createPostRequestDto.getUserId());
			post.setUser(user);
			em.persist(post);
			dtoMetaData = new DtoMetaData("게시물 저장 성공");
			return new CreatePostResponseDto(dtoMetaData, post.getId());
		} catch (Exception e) {
			dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
			return new CreatePostResponseDto(dtoMetaData);
		}
	}

	public Post findById(Long postId) {
		return postRepository.findById(postId)
				.orElseThrow(() -> new NullPointerException(ErrorMessages.POST_NOT_FOUND_ERROR));
	}

	private Post makePostEntity(CreatePostRequestDto createPostRequestDto) {
		Restaurant restaurant = restaurantService.saveIfNotExist(createPostRequestDto.getRestaurantName());
		return Post.postBuilder()
				.encodedImage(createPostRequestDto.getFoodImage())
				.text(createPostRequestDto.getDescription())
				.restaurant(restaurant)
				.build();
	}

	public PostListResponseDto findAllAsPostListResponseDto() {
		DtoMetaData dtoMetaData;
		try{
			Page<Post> postPage = postRepository.findAll(PageRequest.of(0, 10));
			List<Post> postList = postPage.toList();
			List<PostDto> postDtoList = makePostListToPostDtoList(postList);
			dtoMetaData = new DtoMetaData("사진을 정상적으로 조회했습니다.");
			return new PostListResponseDto(dtoMetaData, postDtoList);
		} catch (Exception e){
			dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
			return new PostListResponseDto(dtoMetaData);
		}
	}

	private List<PostDto> makePostListToPostDtoList(List<Post> postList) {
		return postList.stream()
				.map(PostDto::new)
				.collect(Collectors.toList());
	}
}
