package com.example.hhmm.Comment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    private Long commentId;

    @NotEmpty(message="닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message="댓글 내용은 필수항목입니다.")
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int good;   
    private int bad;
    private Long postId;

    public CommentDTO(Comment comment) {    
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.regDate = comment.getRegDate();
        this.updateDate = comment.getUpdateDate();
        this.good = comment.getGood();
        this.bad = comment.getBad();
        this.postId = comment.getPostId();
    }
}
