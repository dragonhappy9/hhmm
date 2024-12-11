package com.example.hhmm.Item;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Data;

@Data
public class ItemLogId implements Serializable {

    private Item item;
    private LocalDate soldDate;

    public ItemLogId() {}

    public ItemLogId(Item item, LocalDate soldDate) {
        this.item = item;
        this.soldDate = soldDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLogId that = (ItemLogId) o;
        return Objects.equals(item, that.item) && Objects.equals(soldDate, that.soldDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, soldDate);
    }
}

