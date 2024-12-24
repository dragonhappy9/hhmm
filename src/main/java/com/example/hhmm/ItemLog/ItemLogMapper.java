package com.example.hhmm.ItemLog;

import com.example.hhmm.Item.ItemMapper;

public class ItemLogMapper {

    public static ItemLog toEntity(ItemLogDTO itemLogDTO){
        ItemLog itemLog = new ItemLog();
        itemLog.setItem(ItemMapper.toEntity(itemLogDTO.getItemDTO()));
        itemLog.setSoldDate(itemLogDTO.getSoldDate());
        itemLog.setSoldQuantity(itemLogDTO.getSoldQuantity());
        return itemLog;
    }
    
    public static ItemLogDTO toDTO(ItemLog itemLog){
        return new ItemLogDTO(
            ItemMapper.toDTO(itemLog.getItem()),
            itemLog.getSoldDate(),
            itemLog.getSoldQuantity()
        );
    }
}
