package com.example.hhmm.Post;

import java.time.LocalDateTime;
import java.util.List;

import com.example.hhmm.Comment.CommentDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Long postId;

    private String nickname;

    @NotEmpty(message="게시글 제목은 필수항목입니다.")
    @Size(max=50, message="게시글 제목은 최대 50글자입니다.")
    private String title;

    @NotEmpty(message="게시글 내용은 필수항목입니다.")
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private int starpoint;
    private List<CommentDTO> commentDTOs;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getRegDate();
        this.updateDate = post.getUpdateDate();
    }
}