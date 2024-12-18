package com.app.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {

    @NotEmpty(message = "please provide username or email")
    private String usernameOrEmail;
    @NotEmpty(message = "please provide password")
    private String password;
}
