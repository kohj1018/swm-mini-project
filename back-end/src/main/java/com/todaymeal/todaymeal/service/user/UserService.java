package com.todaymeal.todaymeal.service.user;

import com.todaymeal.todaymeal.domain.user.User;
import com.todaymeal.todaymeal.domain.user.UserRepository;
import com.todaymeal.todaymeal.web.dto.requestDto.UserFetchRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;

	public User fetchAccount(UserFetchRequestDto requestDto) {
		// id 가 null 이면: 요청을 보낸 사용자의 id 를 찾아 계정 정보 가져오기
		// id 가 null 이 아니면: requestDto 의 id 그대로 가져오기
		Long id = (requestDto.getId() == null) ? (this.fetchCurrentAccountId()) : (requestDto.getId());

		// 계정 id 를 받아 DB 에서 검색 후 반환
		return userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 유저가 없습니다."));
	}

	// 현재 사용자의 id 가져오기
	public Long fetchCurrentAccountId() {
		// Security Context 에서 현재 Authentication 가져오기
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new RuntimeException("Security Context 에 인증 정보가 없습니다.");
		}

		// Authentication 의 name (email) 을 이용하여 사용자의 id 가져오기
		User user = userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 유저가 없습니다."));

		return user.getId();
	}

	public User findCurrentAccountUser() {
		return userRepository.getById(fetchCurrentAccountId());
	}

	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
	}
}
