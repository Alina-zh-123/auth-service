package com.innowise.authservice.dto;
import com.innowise.authservice.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @NotNull(message = "Role cannot be null")
    Role role;
}
