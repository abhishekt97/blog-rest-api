package com.app.blog.service;

import com.app.blog.dto.PostDTO;
import com.app.blog.dto.PostResponse;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDTO getPostById(Long id);

    PostDTO updatePost(Long id, PostDTO postDTO);

    void deletePost(long id);
}
