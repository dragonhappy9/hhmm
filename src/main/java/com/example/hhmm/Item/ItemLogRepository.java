package com.example.hhmm.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.time.LocalDate;


public interface ItemLogRepository extends JpaRepository<ItemLog, ItemLogId> {
    @Query(value = "SELECT * FROM item_log WHERE sold_date = :soldDate ORDER BY sold_quantity DESC", nativeQuery = true)
    List<ItemLog> findRankedBySoldDate(LocalDate soldDate);
    
}
