package com.example.hhmm.DTO;

import com.example.hhmm.Entity.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private String itemName;

    private Integer price;

    private Integer quantity;

    public ItemDTO(Item item){
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getPrice();
    }
}
