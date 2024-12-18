package com.app.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {

    private long id;
    @NotEmpty(message = "name should not be empty.")
    @Size(min = 2, message = "name should be at least 2 characters")
    private String name;
    @Email(message = "must provide valid email.")
    @NotEmpty(message = "email should not be empty.")
    private String email;
    @NotEmpty(message = "comment can't be empty.")
    private String body;
}
