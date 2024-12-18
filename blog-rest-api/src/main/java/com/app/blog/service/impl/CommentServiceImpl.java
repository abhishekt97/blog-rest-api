package com.app.blog.service.impl;
import com.app.blog.dto.CommentDTO;
import com.app.blog.entity.Comment;
import com.app.blog.entity.Post;
import com.app.blog.exception.CommentNotInPostException;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.mapper.Mapper;
import com.app.blog.repository.CommentRepository;
import com.app.blog.repository.PostRepository;
import com.app.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = Mapper.mapToCommentEntity(commentDTO);
        //set post to comment.
        comment.setPost(post);
        //save comment in db
        Comment newComment = commentRepository.save(comment);

        return Mapper.mapToCommentDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(Mapper::mapToCommentDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {

        Comment comment = getComment(postId, commentId);

        return Mapper.mapToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {

        Comment comment = getComment(postId, commentId);

        comment.setBody(commentDTO.getBody());
        comment.setName(commentDTO.getName());
        Comment updatedComment = commentRepository.save(comment);

        return Mapper.mapToCommentDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = getComment(postId, commentId);
        commentRepository.delete(comment);
    }

    //extracted method as it's getting using multiple times
    private Comment getComment(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new CommentNotInPostException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to this post");
        }
        return comment;
    }

}
