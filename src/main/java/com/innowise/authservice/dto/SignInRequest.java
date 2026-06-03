package com.innowise.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @Size(max = 100, message = "Login should be up to 100 characters")
    @NotBlank(message = "Login cannot be blank")
    private String login;

    @Size(min = 4, message = "Password should be more than 4 characters")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
