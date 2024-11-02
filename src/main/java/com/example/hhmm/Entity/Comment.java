package com.example.hhmm.Entity;

import java.time.LocalDateTime;

import com.example.hhmm.DTO.CommentDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition= "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regDate;

    @Column(nullable = false)
    private int good;

    @Column(nullable = false)
    private int bad;

    @Column(name = "postId", nullable = false)
    private Long postId;

    @PrePersist
    protected void onCreate() {
        if (this.regDate == null) {
            this.regDate = LocalDateTime.now();
        }
        this.good = 0;
        this.bad = 0;
    }

    public Comment(CommentDTO commentDTO){
        this.content = commentDTO.getContent();
        this.nickname = commentDTO.getNickname();
        this.postId = commentDTO.getPostId();
    }
}
