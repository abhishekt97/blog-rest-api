package com.app.blog.controller;

import com.app.blog.dto.CommentDTO;
import com.app.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bear Authentication")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            tags = "NEW COMMENT",
            description = "add a new comment on the post.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error!"
                    )
            }
    )
    @PostMapping("/posts/{postId}/comments/new")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @Operation(
            tags = "GET COMMENTS ON A POST",
            description = "get comments on the post",
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
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentByPostId(@PathVariable("postId") long postId){
        return new ResponseEntity<>(commentService.getCommentByPostId(postId), HttpStatus.OK);
    }

    @Operation(
            tags = "GET COMMENT",
            description = "get a specific comment on a post.",
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
    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable("postId") long postId,
                                                     @PathVariable("commentId") long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            tags = "UPDATE COMMENT",
            description = "update a comment.",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error!"
                    )
            }
    )
    @PutMapping("/posts/{postId}/comment/{commentId}/update")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") long postId,
                                                    @PathVariable("commentId") long commentId,
                                                    @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDTO), HttpStatus.OK);
    }

    @Hidden
    @DeleteMapping("/posts/{postId}/comment/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("commentId") long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
