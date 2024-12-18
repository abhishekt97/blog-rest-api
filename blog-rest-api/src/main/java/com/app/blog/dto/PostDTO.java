package com.app.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private long id;
    @NotEmpty(message = "title cannot be empty")
    @Size(min = 2, message = "title should have at least 2 characters.")
    private String title;
    @NotEmpty(message = "description cannot be empty")
    @Size(min = 10, message = "description should have at least 10 characters.")
    private String description;
    @NotEmpty(message = "cannot be an empty blog.")
    private String content;
    private Set<CommentDTO> comments;
}
