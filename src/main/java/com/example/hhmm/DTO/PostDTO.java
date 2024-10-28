package com.example.hhmm.DTO;

import java.time.LocalDateTime;

import com.example.hhmm.Entity.Post;

import lombok.Data;

@Data
public class PostDTO {

    private Long postId;

    private String nickname;

    private String title;
    
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
