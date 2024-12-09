package com.example.hhmm.Item;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)  // 공유 락
    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Optional<Item> findWithSharedLock(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)  // 독점 락
    @Query("SELECT i FROM Item i WHERE i.id = :id")
    Optional<Item> findWithExclusiveLock(@Param("id") Long id);
}

