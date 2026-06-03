package com.innowise.authservice.service;

import com.innowise.authservice.dto.SignInRequest;
import com.innowise.authservice.dto.SignUpRequest;
import com.innowise.authservice.dto.TokenResponse;

import java.util.Map;

public interface AuthenticationService {
    TokenResponse signUp(SignUpRequest request);
    TokenResponse signIn(SignInRequest request);
    Map<String, Object> validate(String token);
    TokenResponse refresh(String refreshToken);
}
