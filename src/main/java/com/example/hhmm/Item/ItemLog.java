package com.example.hhmm.Item;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@IdClass(ItemLogId.class)
public class ItemLog {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Id
    @Column(nullable = false)
    private LocalDate soldDate;

    @Column(nullable = false)
    private int soldQuantity;

    public ItemLog(ItemLogId itemLogId, int quantity){
        this.item = itemLogId.getItem();
        this.soldDate = itemLogId.getSoldDate();
        this.soldQuantity = quantity;
    }
}
