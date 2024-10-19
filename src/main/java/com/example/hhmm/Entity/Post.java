package com.example.hhmm.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(length = 50, nullable = false)
    private String title;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition= "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime reg_date = LocalDateTime.now();

    @Column(nullable = false)
    private int view_count = 0;

    @Column(nullable = false)
    private int good = 0;

    @Column(nullable = false)
    private int bad = 0;

    @PrePersist
    protected void onCreate() {
        this.reg_date = LocalDateTime.now();
    }

    public Post(){}

}
