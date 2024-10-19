package com.example.hhmm.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.example.hhmm.Entity.Comment;
import com.example.hhmm.Repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    // postService의 의존성을 주입한다는 의미
    private final PostService postService;

    @Transactional
    public Comment createComment(Long postId, Comment comment) {
        // 댓글을 추가하기 전에 해당 postId가 존재하는지 확인
        postService.getPost(postId);
        comment.setPostId(postId);
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
