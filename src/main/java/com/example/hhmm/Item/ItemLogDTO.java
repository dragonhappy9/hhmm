package com.example.hhmm.Item;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemLogDTO {

    private ItemDTO itemDTO;
    private LocalDate soldDate;
    private int soldQuantity;
    
    public static ItemLogDTO toDTO(ItemLog itemLog){
        return new ItemLogDTO(
            ItemDTO.toDTO(itemLog.getItem()),
            itemLog.getSoldDate(),
            itemLog.getSoldQuantity()
        );
    }
}
