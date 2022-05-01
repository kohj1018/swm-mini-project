package com.todaymeal.todaymeal.web.controller;

import com.todaymeal.todaymeal.domain.user.User;
import com.todaymeal.todaymeal.web.dto.requestDto.UserFetchRequestDto;
import com.todaymeal.todaymeal.web.dto.responseDto.UserFetchResponseDto;
import com.todaymeal.todaymeal.service.user.UserService;
import com.todaymeal.todaymeal.global.dto.DtoMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    private final UserService userService;

    // 계정 가져오기: 모든 사용자 계정 정보를 가져올 수 있음
    @GetMapping
    public ResponseEntity<UserFetchResponseDto> fetchAccount(@RequestBody UserFetchRequestDto requestDto) {
        DtoMetaData dtoMetaData;

        try {
            User user = userService.fetchAccount(requestDto);
            dtoMetaData = new DtoMetaData("유저 정보 가져오기 성공");
            return ResponseEntity.ok(new UserFetchResponseDto(dtoMetaData, user));
        } catch (Exception e) {
            dtoMetaData = new DtoMetaData(e.getMessage(), e.getClass().getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserFetchResponseDto(dtoMetaData));
        }
    }
}
