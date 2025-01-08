package com.example.hhmm.Post;

import java.time.LocalDateTime;
import java.util.List;

import com.example.hhmm.Comment.CommentDTO;
import com.example.hhmm.Item.ItemDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class PostDTO {

    private Long id;
    private String title;

    @NotBlank(message="상품 설명은 필수항목입니다.")
    private String itemDescript;
    private String name;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private int viewCount;
    private float starpoint;
    private List<CommentDTO> commentDTOs;

    @Valid
    private ItemDTO itemDTO;

    public PostDTO(Long id,
        String title,
        String itemDescript,
        String name,
        LocalDateTime regDate,
        LocalDateTime updateDate,
        int viewCount,
        float starpoint,
        ItemDTO itemDTO,
        List<CommentDTO> commentDTOs
    ){
        this.id = id;
        this.title = title;
        this.itemDescript = itemDescript;
        this.name = name;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.viewCount = viewCount;
        this.starpoint = starpoint;
        this.itemDTO = itemDTO;
        this.commentDTOs = commentDTOs;
    }
}