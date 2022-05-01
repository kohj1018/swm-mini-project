package com.todaymeal.todaymeal.global.security;

import com.todaymeal.todaymeal.global.jwt.JwtAccessDeniedHandler;
import com.todaymeal.todaymeal.global.jwt.JwtAuthenticationEntryPoint;
import com.todaymeal.todaymeal.global.jwt.JwtSecurityConfig;
import com.todaymeal.todaymeal.global.jwt.JwtTokenProvider;
import com.todaymeal.todaymeal.global.oauth.CustomOAuth2UserService;
import com.todaymeal.todaymeal.global.oauth.CustomOAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .httpBasic().disable()
                .headers().frameOptions().disable()

                // JWT Exception Handling
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)

                // JwtCustomFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                // 세션을 Stateless 로 설정
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 로그인, H2 Console 은 인증 없이 접근 허용
                .and()
                .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .antMatchers("/", "/h2-console/**").permitAll()
                // 이외 API 는 인증 필요
                    .anyRequest().authenticated()

                // OAuth2 로그인
                .and()
                .oauth2Login()
                    // 로그인 성공 이후 받아온 사용자 정보를 커스텀 핸들러와 커스텀 서비스 안에서 처리
                    .successHandler(customOAuth2SuccessHandler)
                    .userInfoEndpoint().userService(customOAuth2UserService);
    }
}
