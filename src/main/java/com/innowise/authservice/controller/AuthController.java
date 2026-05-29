package com.innowise.authservice.controller;

import com.innowise.authservice.dto.*;
import com.innowise.authservice.service.AuthenticationService;
import com.innowise.authservice.service.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @PostMapping("/sign-up")
    ResponseEntity<TokenResponse> signUp(@RequestBody @Valid SignUpRequest request){
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/sign-in")
    ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequest request){
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(@RequestBody @Valid ValidateTokenRequest request) {
        return ResponseEntity.ok(tokenService.validate(request.getToken()));
    }

    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request){
        return ResponseEntity.ok(authenticationService.refresh(request.getRefreshToken()));
    }
}

