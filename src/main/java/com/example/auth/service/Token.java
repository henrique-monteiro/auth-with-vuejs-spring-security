package com.example.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class Token {
    @Getter
    private final String token;

    private Token(String token) {
        this.token = token;
    }

    public static Token of(Long userId, Long validityInMinutes, String secretKey){
        var issueDate = Instant.now();
        return new Token(
                Jwts.builder()
                        .claim("userId", userId)
                        .setIssuedAt(Date.from(issueDate))
                        .setExpiration(Date.from(issueDate.plus(validityInMinutes, ChronoUnit.MINUTES)))
                        .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                        .compact());
    }

    public static Token of(String token){
        return new Token(token);
    }
    
    public static Long from(String token, String secretKey){
        String t = token;
        String sK = secretKey;
        Long setSign = ((Claims) Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parse(token)
                .getBody())
//                .getId();
                .get("userId", Long.class);

        return ((Claims) Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parse(token)
                .getBody())
//                .getId();
                .get("userId", Long.class);
    }
}
