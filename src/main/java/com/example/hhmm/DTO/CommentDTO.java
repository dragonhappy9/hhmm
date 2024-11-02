package com.example.hhmm.DTO;

import com.example.hhmm.Entity.Comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {

    @NotEmpty(message="댓글 내용은 필수항목입니다.")
    private String content;

    @NotEmpty(message="닉네임은 필수항목입니다.")
    private String nickname;

    private Long postId;

    public CommentDTO(Comment comment) {    
        this.content = comment.getContent();
        this.nickname = comment.getNickname();
        this.postId = comment.getPostId();
    }
}
