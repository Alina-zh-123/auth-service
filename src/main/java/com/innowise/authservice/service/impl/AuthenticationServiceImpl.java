package com.innowise.authservice.service.impl;

import com.innowise.authservice.dto.SignInRequest;
import com.innowise.authservice.dto.SignUpRequest;
import com.innowise.authservice.dto.TokenResponse;
import com.innowise.authservice.entity.UserData;
import com.innowise.authservice.exception.UserDataException;
import com.innowise.authservice.repository.UserDataRepository;
import com.innowise.authservice.service.AuthenticationService;
import com.innowise.authservice.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserDataRepository userDataRepository;

    public TokenResponse signUp(SignUpRequest request) {
        if (userDataRepository.existsByLogin(request.getLogin())) {
            throw new UserDataException("User with login '" + request.getLogin() + "' already exists!");
        }

        if (userDataRepository.existsByEmail(request.getEmail())) {
            throw new UserDataException("User with email '" + request.getEmail() + "' already exists!");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserData userData = UserData.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(request.getRole())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        userDataRepository.save(userData);

        return new TokenResponse(
                tokenService.generateAccessToken(userData),
                tokenService.generateRefreshToken(userData)
        );
    }

    public TokenResponse signIn(SignInRequest request) {
        UserData userData = userDataRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new UserDataException("User is not found!"));

        if (!passwordEncoder.matches(request.getPassword(), userData.getPassword())) {
            throw new UserDataException("Invalid login or password");
        }

        return new TokenResponse(
                tokenService.generateAccessToken(userData),
                tokenService.generateRefreshToken(userData)
        );
    }

    public Map<String, Object> validate(String token) {
        return tokenService.validate(token);
    }

    public TokenResponse refresh(String refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }
}
