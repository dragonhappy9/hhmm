package com.example.hhmm.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long comment_id;

    private String content;

    private String nickname;

    private LocalDateTime reg_date;

    private int good;

    private int bad;
}
