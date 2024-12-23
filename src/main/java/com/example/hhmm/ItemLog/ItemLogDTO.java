package com.example.hhmm.ItemLog;

import java.time.LocalDate;

import com.example.hhmm.Item.ItemDTO;

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
}
