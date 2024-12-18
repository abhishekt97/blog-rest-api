package com.app.blog.mapper;

import com.app.blog.dto.CommentDTO;
import com.app.blog.dto.PostDTO;
import com.app.blog.entity.Comment;
import com.app.blog.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class Mapper {

    @Autowired
    private static ModelMapper modelMapper = new ModelMapper();



    public static PostDTO mapToDTO(Post post){
        return modelMapper.map(post, PostDTO.class);
    }

    public static Post mapToEntity(PostDTO postDTO){
        return modelMapper.map(postDTO, Post.class);
    }

    public static CommentDTO mapToCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());
        commentDTO.setName(comment.getName());
        return commentDTO;
    }

    public static Comment mapToCommentEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        return comment;
    }

}
