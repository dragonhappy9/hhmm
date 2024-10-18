package com.example.hhmm.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class MyBucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketId;

    @OneToOne(mappedBy = "myBucket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToMany
    @JoinTable(
        name = "bucketItem", 
        joinColumns = @JoinColumn(name = "bucketId"), 
        inverseJoinColumns = @JoinColumn(name = "itemId") 
    )
    private List<Item> itemList = new ArrayList<>();

    public MyBucket() {}

    // 아이템 삽입
    public void addItem(Item item){
        itemList.add(item);
    }

    // 아이템 삭제
    public void removeItem(Item item){
        itemList.remove(item);
    }
}
