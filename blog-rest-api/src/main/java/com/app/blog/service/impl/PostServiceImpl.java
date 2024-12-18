package com.app.blog.service.impl;

import com.app.blog.dto.PostDTO;
import com.app.blog.dto.PostResponse;
import com.app.blog.entity.Post;
import com.app.blog.exception.ResourceNotFoundException;
import com.app.blog.mapper.Mapper;
import com.app.blog.repository.PostRepository;
import com.app.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = Mapper.mapToEntity(postDTO);
        Post saved = postRepository.save(post);
        return Mapper.mapToDTO(saved);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable request
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);

        //get content for page object
        List<Post> posts = page.getContent();

        //list post entity to dto
        List<PostDTO> postDTOList = posts.stream().map(Mapper::mapToDTO).toList();

        //Response instantiation
        PostResponse response = new PostResponse();
        response.setContent(postDTOList);
        response.setPageNo(page.getNumber());
        response.setPageSize(page.getSize());
        response.setLastPage(page.isLast());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());

        return response;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return Mapper.mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post updated = postRepository.save(post);

        return Mapper.mapToDTO(updated);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

}
