package com.example.hhmm.Item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.example.hhmm.Bucket.BucketItem;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

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

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)    
    private List<BucketItem> bucketList = new ArrayList<>();

    public Item(ItemDTO itemDTO){
        this.itemName = itemDTO.getItemName();
        this.price = itemDTO.getPrice();
        this.quantity = itemDTO.getQuantity();
        this.filePath = (itemDTO.getFilePath() == null || itemDTO.getFilePath().isEmpty())
        ? "등록된 파일이 없습니다." : itemDTO.getFilePath();
        this.filePath = itemDTO.getFilePath();
    }
}
