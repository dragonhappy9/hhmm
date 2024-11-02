package com.example.hhmm.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.hhmm.DTO.PostDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor  // 기본 생성자를 자동으로 생성해주는 어노테이션
@Getter             // Getter와 Setter를 자동으로 생성해주는 각각의 어노테이션
@Setter
@Entity             // 현재 클래스를 Entity클래스로 설정해주는 어노테이션
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
    private LocalDateTime regDate;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int good;

    @Column(nullable = false)
    private int bad;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)     // 부모 Entity가 삭제될 시 자동으로 자식 Entity를 삭제해주게 옵션설정!
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.regDate == null) {
            this.regDate = LocalDateTime.now();     // Post가 생성될 때 현재 시간을 저장
        }
        this.viewCount = 0;
        this.good = 0;
        this.bad = 0;
    }

    public Post (PostDTO postDTO){
        if (postDTO.getTitle() == null || postDTO.getContent() == null || postDTO.getNickname() == null) {
            throw new IllegalArgumentException("제목 또는 내용 혹은 닉네임이 작성되지 않았습니다.");
        }
        this.title = postDTO.getTitle();
        this.content = postDTO.getContent();
        this.nickname = postDTO.getNickname();
    }
}
