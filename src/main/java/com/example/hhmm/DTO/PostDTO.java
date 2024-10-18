package com.example.hhmm.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostDTO {

    private Long post_id;

    private String nickname;

    private String title;
    
    private String content;

    private LocalDateTime reg_date;

    private int view_count;

    private int good;

    private int bad;

}
