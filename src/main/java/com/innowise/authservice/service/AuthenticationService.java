package com.innowise.authservice.service;

import com.innowise.authservice.dto.SignInRequest;
import com.innowise.authservice.dto.SignUpRequest;
import com.innowise.authservice.dto.TokenResponse;
import com.innowise.authservice.entity.Role;
import com.innowise.authservice.entity.UserData;
import com.innowise.authservice.exception.UserDataException;
import com.innowise.authservice.repository.UserDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserDataRepository userDataRepository;

    public TokenResponse signUp(SignUpRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserData userData = UserData.builder()
                .uuid(UUID.randomUUID())
                .login(request.getLogin())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(Role.ROLE_USER)
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
            throw new BadCredentialsException("Invalid login or password");
        }

        return new TokenResponse(
                tokenService.generateAccessToken(userData),
                tokenService.generateRefreshToken(userData)
        );
    }

    public TokenResponse refresh(String refreshToken) {
        if (!tokenService.validateToken(refreshToken)) {
            throw new AuthenticationException("Invalid refresh token") {};
        }

        String login = tokenService.extractLogin(refreshToken);

        UserData userData = userDataRepository.findByLogin(login)
                .orElseThrow(() -> new UserDataException("User is not found!"));

        String newAccessToken = tokenService.generateAccessToken(userData);
        String newRefreshToken = tokenService.generateRefreshToken(userData);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
