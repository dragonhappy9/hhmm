package com.example.hhmm.DTO;

import lombok.Data;
import java.time.LocalDateTime;

import com.example.hhmm.Entity.Comment;

import jakarta.validation.constraints.NotEmpty;

@Data
public class CommentDTO {

    private Long commentId;

    @NotEmpty(message="댓글 내용은 필수항목입니다.")
    private String content;

    @NotEmpty(message="닉네임은 필수항목입니다.")
    private String nickname;

    private LocalDateTime regDate;

    private int good;

    private int bad;

    private Long postId;

    public CommentDTO(){}
    
    public CommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.nickname = comment.getNickname();
        this.regDate = comment.getReg_date();
        this.good = comment.getGood();
        this.bad = comment.getBad();
        this.postId = comment.getPostId();
    }
}
