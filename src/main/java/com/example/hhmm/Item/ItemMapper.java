package com.example.hhmm.Item;

public class ItemMapper {
    public static Item toEntity(ItemDTO itemDTO){
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setItemName(itemDTO.getItemName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        item.setFilePath(itemDTO.getFilePath());
        return item;
    }

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
