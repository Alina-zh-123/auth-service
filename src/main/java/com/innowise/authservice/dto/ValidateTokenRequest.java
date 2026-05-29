package com.innowise.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ValidateTokenRequest {
    @NotBlank(message = "Token cannot be blank")
    private String token;
}
