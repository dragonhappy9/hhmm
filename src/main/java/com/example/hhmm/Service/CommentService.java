package com.example.hhmm.Service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.DTO.CommentDTO;
import com.example.hhmm.Entity.Comment;
import com.example.hhmm.Entity.Post;
import com.example.hhmm.Repository.CommentRepository;
import com.example.hhmm.Repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // Comment Create
    public void CreateComment(CommentDTO commentDTO, Long postId){
        // 아래 Comment Read의 if else문을 간략화한 버전
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new DataNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(commentDTO.getContent());
        comment.setNickname(commentDTO.getNickname());
        comment.setGood(0);
        comment.setBad(0);
        comment.setReg_date(null);
        this.commentRepository.save(comment);
    }

    // Comment Read
    public Comment ReadComment(Long commentId){
        Optional<Comment> comment = this.commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("Comment not found");
        }
    }

    // Comment Update
    public void UpdateComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        this.commentRepository.save(comment);
    }

    // Comment Delete
    public void DeleteComment(Comment comment){
        this.commentRepository.delete(comment);
    }
}
