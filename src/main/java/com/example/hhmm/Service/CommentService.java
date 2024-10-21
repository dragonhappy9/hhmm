package com.example.hhmm.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.hhmm.DTO.CommentDTO;
import com.example.hhmm.DTO.PostDTO;
import com.example.hhmm.Entity.Comment;
import com.example.hhmm.Repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    // postService의 의존성을 주입한다는 의미
    private final PostService postService;

    // get CommentList
    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
            .map(CommentDTO::new) // 엔티티를 DTO로 변환
            .collect(Collectors.toList());
    }

    // Comment Create 
    @Transactional
    public void createComment(Long postId, CommentDTO commentDTO) {
        PostDTO postDTO = postService.getPost(postId); 
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setNickname(commentDTO.getNickname());
        comment.setGood(0);
        comment.setBad(0);
        comment.setReg_date(LocalDateTime.now());
        comment.setPostId(postDTO.getPostId());
        commentRepository.save(comment);
    }

    // Comment Update
    @Transactional
    public void updateComment(Long commentId, CommentDTO commentDTO){
        Comment comment = this.commentRepository.findById(commentId).get();
        comment.setContent(commentDTO.getContent());
        comment.setReg_date(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    // Comment Delete 
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
