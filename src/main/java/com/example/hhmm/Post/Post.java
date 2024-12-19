package com.example.hhmm.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.hhmm.Comment.Comment;
import com.example.hhmm.Item.Item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자를 자동으로 생성해주는 어노테이션
@Data
@Entity // 현재 클래스를 Entity클래스로 설정해주는 어노테이션
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String itemDescript;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, columnDefinition= "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regDate;

    @Column(nullable = true)
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private float starpoint;

    // post 생성, 삭제시 item도 생성 삭제 자동 / post불러올 때 항상 같이 불러오기
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    // post 삭제시 comment도 생성 삭제 자동 / post불러올 때 같이불러오지 않고 comment에 접근시 불러오기
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)     
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();

    // 데이터베이스에 저장되기 직전 자동 호출되어 초기화
    @PrePersist
    protected void onCreate() {
        if (this.regDate == null) {
            this.regDate = LocalDateTime.now();
        }
        this.updateDate = null;
        this.viewCount = 0;
        this.starpoint = 0;
    }
}
