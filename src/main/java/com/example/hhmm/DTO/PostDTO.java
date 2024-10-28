package com.example.hhmm.DTO;

import java.time.LocalDateTime;

import com.example.hhmm.Entity.Post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO {

    private Long postId;

    @NotEmpty(message="닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message="게시글 제목은 필수항목입니다.")
    @Size(max=50, message="게시글 제목은 최대 50글자입니다.")
    private String title;
    
    @NotEmpty(message="게시글 내용은 필수항목입니다.")
    private String content;

    private LocalDateTime regDate;

    private int viewCount;

    private int good;

    private int bad;

    public PostDTO(){};

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getNickname();
        this.viewCount = post.getViewCount();
        this.good = post.getGood();
        this.bad = post.getBad();
        this.regDate = post.getRegDate();
    }

}
