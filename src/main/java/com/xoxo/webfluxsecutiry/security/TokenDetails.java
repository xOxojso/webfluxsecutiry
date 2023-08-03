package com.xoxo.webfluxsecutiry.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails {
    private Long id;
    private String token;
    private Date issuedAt;
    private Date expiresAt;
}
