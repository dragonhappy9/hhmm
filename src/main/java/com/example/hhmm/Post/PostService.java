package com.example.hhmm.Post;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.Exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // Post 목록 가져오기
    @Transactional(readOnly = true)
    public Page<PostDTO> getPostList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostDTO::new);
    }

    // Post 가져오기
    @Transactional(readOnly = true)
    public PostDTO getPost(Long postId){
        Post post = this.postRepository.findById(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found"));
        PostDTO postDTO = new PostDTO(post);
        return postDTO;
    }

    // Post Create
    @Transactional
    public void createPost(PostDTO postDTO){
        Post post = new Post(postDTO);
        this.postRepository.save(post);
    }

    // Post Read
    @Transactional
    public PostDTO readPost(Long postId) {
        Post post = this.postRepository.findByIdWithComments(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);  // 조회수 증가
        PostDTO postDTO = new PostDTO(post);
        return postDTO;
    }

    // Post Update
    @Transactional
    public void updatePost(Long postId, PostDTO postDTO){
        Post post = this.postRepository.findById(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found")); 
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUpdateDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    // Post Delete
    @Transactional
    public void deletePost(Long postId){
        this.postRepository.delete(this.postRepository.findById(postId).get());;
    }
}
