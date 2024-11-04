package com.example.hhmm.Comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // get CommentList
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
            .map(CommentDTO::new)
            .collect(Collectors.toList());
    }

    // Comment Create 
    @Transactional
    public void createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = new Comment(commentDTO);
        commentRepository.save(comment);
    }

    // Comment Update
    @Transactional
    public void updateComment(Long commentId, CommentDTO commentDTO){
        Comment comment = this.commentRepository.findById(commentId).get();
        comment.setContent(commentDTO.getContent());
        comment.setRegDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    // Comment Delete 
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
