package com.example.hhmm.Item;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private Long itemId;

    @NotBlank(message = "상품명은 필수항목입니다.")
    private String itemName;

    @Positive(message = "0원은 등록할 수 없습니다.")
    @NotNull(message = "올바른 가격을 입력해주세요")
    private Integer price;

    @PositiveOrZero(message = "수량은 0이상이어야 합니다.")
    @NotNull(message = "수량은 0이상이어야 합니다.")
    private Integer quantity;

    @Column(nullable = true)
    private String filePath;

    public ItemDTO(Item item){
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.filePath = item.getFilePath();
    }
}
