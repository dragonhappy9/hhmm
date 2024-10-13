package com.example.hhmm.Repository;

import org.springframework.stereotype.Repository;

import com.example.hhmm.Entity.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();

    // 아이템 저장
    public Item save(Item item) {
        store.put(item.getId(), item);
        return item;
    }

    // 아이템 아이디 찾기
    public Item findById(Long id) {
        return store.get(id);
    }

    // 아이템 전체 반환
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 아이템 업데이트
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
    
    // 아이템 전체 삭제
    public void clearStore() {
        store.clear();
    }
}

