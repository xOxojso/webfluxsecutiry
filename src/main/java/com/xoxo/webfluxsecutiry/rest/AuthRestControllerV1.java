package com.xoxo.webfluxsecutiry.rest;

import com.xoxo.webfluxsecutiry.dto.AuthRequestDto;
import com.xoxo.webfluxsecutiry.dto.AuthResponseDto;
import com.xoxo.webfluxsecutiry.dto.UserDto;
import com.xoxo.webfluxsecutiry.entity.UserEntity;
import com.xoxo.webfluxsecutiry.mapper.UserMapper;
import com.xoxo.webfluxsecutiry.security.CustomPrincipal;
import com.xoxo.webfluxsecutiry.security.SecurityService;
import com.xoxo.webfluxsecutiry.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto) {
        UserEntity entity = userMapper.map(userDto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return securityService.authenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(AuthResponseDto.builder()
                        .userId(tokenDetails.getId())
                        .token(tokenDetails.getToken())
                        .issuedAt(tokenDetails.getIssuedAt())
                        .expiresAt(tokenDetails.getExpiresAt())
                        .build()));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(principal.getId())
                .map(userMapper::map);
    }
}
