package com.week6.AuthFlow.dtos;

import com.week6.AuthFlow.validators.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpDTO {
    @NotBlank
    @NotNull
    private String username;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @StrongPassword
    private String password;
}
