package com.example.hhmm.Item;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

    @NotEmpty(message = "상품명은 필수항목입니다.")
    private String itemName;

    @NotEmpty(message = "가격은 필수항목입니다.")
    private Integer price;

    private Integer quantity;

    public ItemDTO(Item item){
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getPrice();
    }
}
