package com.example.hhmm.Item;

import java.util.ArrayList;
import java.util.List;

import com.example.hhmm.Bucket.BucketItem;
import com.example.hhmm.Post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String itemName;

    @Column(nullable = false)
    @Min(0)
    private Integer price;

    @Column(nullable = false)
    @Min(0)
    private Integer quantity;

    @Column(nullable = true)
    private String filePath;

    @Column(name = "post_id", nullable = true)
    private String postId;

    // 여러 장바구니에 담길수 있음
    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)    
    private List<BucketItem> bucketList = new ArrayList<>();

    // 로그가 여러개 생길수 있음
    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ItemLog> itemLogs = new ArrayList<>();
}
