package com.example.hhmm.Post;

import java.time.LocalDateTime;
import java.util.List;

import com.example.hhmm.Comment.CommentDTO;
import com.example.hhmm.Item.ItemDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Long postId;

    private String nickname;

    @NotBlank(message="상품 설명은 필수항목입니다.")
    private String content;

    @Valid
    private ItemDTO itemDTO = new ItemDTO();

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private float starpoint;

    private List<CommentDTO> commentDTOs;

    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getNickname();
        this.content = post.getContent();
        this.regDate = post.getRegDate();
        this.updateDate = post.getUpdateDate();
        this.viewCount = post.getViewCount();
        this.starpoint = post.getStarpoint();
        this.itemDTO = new ItemDTO(post.getItem());
    }
}