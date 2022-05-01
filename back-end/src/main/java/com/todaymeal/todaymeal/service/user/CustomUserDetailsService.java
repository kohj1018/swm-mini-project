package com.todaymeal.todaymeal.service.user;

import com.todaymeal.todaymeal.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    // username == email
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 를 찾을 수 없습니다."));
    }

    // Account 객체를 UserDetails 객체로 변환
    private UserDetails createUserDetails(com.todaymeal.todaymeal.domain.user.User user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRoleKey());

        return new User(
                String.valueOf(user.getId()),
                null,
                Collections.singleton(grantedAuthority)
        );
    }
}
