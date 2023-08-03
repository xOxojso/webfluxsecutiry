package com.xoxo.webfluxsecutiry.service;

import com.xoxo.webfluxsecutiry.entity.UserEntity;
import com.xoxo.webfluxsecutiry.entity.UserRole;
import com.xoxo.webfluxsecutiry.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserEntity> registerUser(UserEntity userEntity) {
        return userRepository.save(userEntity.toBuilder()
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .role(UserRole.USER)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()).doOnSuccess(user -> {
                    log.info("IN registerUser - user: {} created", user);
        });
    }

    public Mono<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
