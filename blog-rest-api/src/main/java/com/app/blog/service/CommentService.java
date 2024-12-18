package com.app.blog.service;

import com.app.blog.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentByPostId(long postId);

    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);
}
