package com.example.hhmm.Item;

public class ItemMapper {
    public static ItemDTO toDTO(Item item) {
        return new ItemDTO(
            item.getId(),
            item.getItemName(),
            item.getPrice(),
            item.getQuantity(),
            item.getFilePath()
        );
    }
}
