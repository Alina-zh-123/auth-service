package com.innowise.authservice.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @Size(max = 100, message = "Login should be up to 100 characters")
    @NotBlank(message = "Login cannot be blank")
    private String login;

    @Email(message = "Email should be as user@example.com")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min = 4, message = "Password should be more than 4 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
