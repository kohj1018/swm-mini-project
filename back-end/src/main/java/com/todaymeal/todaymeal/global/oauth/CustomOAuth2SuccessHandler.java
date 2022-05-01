package com.todaymeal.todaymeal.global.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaymeal.todaymeal.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        String token = tokenProvider.generateToken(authentication, getOAuth2UserEmail(oAuth2User));

        writeTokenResponse(response, token);
    }

    // 토큰 Response 에 담아 반환
    private void writeTokenResponse(HttpServletResponse response, String token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("token", token);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }

    // 사용자의 email 가져오기 (모든 소셜 로그인에서 동일)
    private String getOAuth2UserEmail(OAuth2User oAuth2User) {
        return (String) oAuth2User.getAttributes().get("email");
    }
}
