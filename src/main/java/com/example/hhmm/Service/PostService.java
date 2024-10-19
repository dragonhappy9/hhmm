package com.example.hhmm.Service;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.DTO.PostDTO;
import com.example.hhmm.Entity.Post;
import com.example.hhmm.Repository.PostRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // Post 목록 가져오기
    @Transactional(readOnly = true)
    public List<Post> getPostList(){
        return this.postRepository.findAll();
    }

    // Post 가져오기
    @Transactional(readOnly = true)
    public Post getPost(Long postId){
        return this.postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post not found"));
    }

    // Post Create
    @Transactional
    public void createPost(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setNickname(postDTO.getNickname());
        post.setGood(0);
        post.setBad(0);
        post.setReg_date(LocalDateTime.now());
        post.setView_count(0);
        this.postRepository.save(post);
    }

    // Post Read
    @Transactional
    public Post readPost(Long postId) {
        Post post = this.postRepository.findById(postId)
                        .orElseThrow(() -> new DataNotFoundException("Post not found"));
        post.setView_count(post.getView_count() + 1);  // 조회수 증가
        return post;
    }

    // Post Update
    @Transactional
    public void updatePost(Long postId, PostDTO postDTO){
        Post post = this.postRepository.findById(postId).get(); 
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setReg_date(LocalDateTime.now());
        this.postRepository.save(post);
    }

    // Post Delete
    @Transactional
    public void deletePost(Long postId){
        this.postRepository.delete(this.postRepository.findById(postId).get());;
    }
}
