package com.example.hhmm.DTO;

import com.example.hhmm.Entity.Post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    @NotEmpty(message="닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message="게시글 제목은 필수항목입니다.")
    @Size(max=50, message="게시글 제목은 최대 50글자입니다.")
    private String title;
    
    @NotEmpty(message="게시글 내용은 필수항목입니다.")
    private String content;

    public PostDTO(Post post) {
        this.nickname = post.getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
