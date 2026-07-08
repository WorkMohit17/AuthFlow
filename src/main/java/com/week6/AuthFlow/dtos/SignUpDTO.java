package com.week6.AuthFlow.dtos;

import com.week6.AuthFlow.validators.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpDTO {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotBlank(message = "Password is required.")
    @StrongPassword(message = "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character.")
    private String password;
}
