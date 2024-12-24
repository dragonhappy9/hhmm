package com.example.hhmm.Comment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {

    private Long id;
    
    @NotEmpty(message="후기를 작성하신다면 내용을 적어주세요.")
    @Size(max = 1000, message = "내용은 최대 1000자까지 입력 가능합니다.")
    private String content;
    private String nickname;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private float starpoint;
}
