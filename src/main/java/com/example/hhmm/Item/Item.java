package com.example.hhmm.Item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column(length = 30, nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)    
    private List<BucketItem> BucketList = new ArrayList<>();

    public Item(ItemDTO itemDTO){
        this.itemName = itemDTO.getItemName();
        this.price = itemDTO.getPrice();
        this.quantity = itemDTO.getQuantity();
    }
}
