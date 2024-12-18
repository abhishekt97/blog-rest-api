package com.app.blog.controller;

import com.app.blog.dto.PostDTO;
import com.app.blog.dto.PostResponse;
import com.app.blog.service.PostService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.app.blog.utils.PostAppConstants.*;

@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "Bear Authentication")
@Tag(name = "APIs for Post Resource")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(
            tags = "NEW POST",
            description = "Create Post REST API is used to save a post into the database.",
            responses = {@ApiResponse(
                    responseCode = "201",
                    description = "Created new post successfully."
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Failed!"
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @Operation(
            tags = "GET ALL POSTS",
            description = "Retrieve all posts from the database.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No Content Found"
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR, required = false)String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(
            tags = "GET SPECIFIC POST",
            description = "Retrieve a single post based on its id from the database.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No Content Found"
                    )
            }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @Operation(
            tags = "UPDATE POST",
            description = "update a post.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No Content Found"
                    )
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.updatePost(id, postDTO), HttpStatus.OK);
    }

    @Hidden //hide the endpoint from swagger documentation
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
