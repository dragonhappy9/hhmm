package com.example.hhmm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.example.hhmm.DTO.ItemDTO;

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

    @ManyToMany(mappedBy = "itemList")      // ManyToMany 관계 이므로 포함된 Bucket의 리스트
    private List<Bucket> Buckets = new ArrayList<>();

    public Item(ItemDTO itemDTO){
        this.itemName = itemDTO.getItemName();
        this.price = itemDTO.getPrice();
        this.quantity = itemDTO.getQuantity();
    }
}
