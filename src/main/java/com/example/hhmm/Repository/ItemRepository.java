package com.example.hhmm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hhmm.Entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

