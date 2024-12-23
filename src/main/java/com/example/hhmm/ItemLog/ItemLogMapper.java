package com.example.hhmm.ItemLog;

import com.example.hhmm.Item.ItemMapper;

public class ItemLogMapper {
    public static ItemLogDTO toDTO(ItemLog itemLog){
        return new ItemLogDTO(
            ItemMapper.toDTO(itemLog.getItem()),
            itemLog.getSoldDate(),
            itemLog.getSoldQuantity()
        );
    }
}
