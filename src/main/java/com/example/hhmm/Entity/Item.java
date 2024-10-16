package com.example.hhmm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
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

    public Item() {}

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity =quantity;
    }
}
