package com.example.hhmm.Comment;

import java.time.LocalDateTime;

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

    @Column(nullable = true)
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private float starpoint;

    @Column(name = "post_id", nullable = true)
    private Long postId;

    @PrePersist
    protected void onCreate() {
        if (this.regDate == null) {
            this.regDate = LocalDateTime.now();
        }
        this.updateDate = null;
    }

    public Comment(CommentDTO commentDTO){
        this.nickname = commentDTO.getNickname();
        this.content = commentDTO.getContent();
        this.postId = commentDTO.getPostId();
        this.starpoint = commentDTO.getStarpoint();
    }
}
