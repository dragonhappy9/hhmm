package com.example.hhmm.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.DTO.ItemDTO;
import com.example.hhmm.Entity.Item;
import com.example.hhmm.Repository.ItemRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
    
    private final ItemRepository itemRepository;

    public List<Item> getList(){
        return this.itemRepository.findAll();
    }
    
    public Item getItem(Long id){
        Optional<Item> item = this.itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    
    public void addItem(ItemDTO itemDTO){
        Item item = new Item();
        item.setItemName(itemDTO.getItemName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        itemRepository.save(item);
    }

}
