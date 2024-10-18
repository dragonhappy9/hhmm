package com.example.hhmm.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.DTO.PostDTO;
import com.example.hhmm.Entity.Comment;
import com.example.hhmm.Entity.Post;
import com.example.hhmm.Repository.CommentRepository;
import com.example.hhmm.Repository.PostRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    LocalDateTime nowTime = LocalDateTime.now();

    public List<Post> getPostList(){
        return this.postRepository.findAll();
    }

    public List<Comment> getCommentList(Long postId){
        List<Comment> commentList = this.commentRepository.findByPost_PostId(postId);
        return commentList;
    }

    // Post Create
    public void CreatePost(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setNickname(postDTO.getNickname());
        post.setGood(0);
        post.setBad(0);
        post.setReg_date(nowTime);
        post.setView_count(0);
        post.setComments(null);
        this.postRepository.save(post);
    }

    // Post Read
    public Post ReadPost(Long postId){
        Optional<Post> post = this.postRepository.findById(postId);
        if (post.isPresent()) {
            // post를 열면 view_count 1증가
            post.get().setView_count(post.get().getView_count() + 1);
            return post.get();
        } else {
            throw new DataNotFoundException("Post not found");
        }
    }

    // Post Update
    public void UpdatePost(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setReg_date(nowTime);
        this.postRepository.save(post);
    }

    // Post Delete
    public void DeletePost(Post post){
        this.postRepository.delete(post);
    }
}
