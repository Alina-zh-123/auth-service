package com.innowise.authservice.service;

import com.innowise.authservice.dto.TokenResponse;
import com.innowise.authservice.entity.UserData;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface TokenService {
    String generateAccessToken(UserData userData);
    String generateRefreshToken(UserData userData);
    TokenResponse refreshToken(String refreshToken);
    String createToken(Map<String, Object> claims, String login, long expirationMs);
    Map<String, Object> validate(String token);
    Key getSignKey();
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String extractLogin(String token);
    String extractTokenType(String token);
    Date extractExpiration(String token);
    Boolean isTokenExpired(String token);
    Boolean validateToken(String token, String expectedType);
}
