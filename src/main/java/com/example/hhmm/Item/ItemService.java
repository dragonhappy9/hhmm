package com.example.hhmm.Item;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
    
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<Item> getList(){
        return this.itemRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Item getItem(Long id){
        Optional<Item> item = this.itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        } else {
            throw new DataNotFoundException("Item not found");
        }
    }
    
    @Transactional
    public void addItem(ItemDTO itemDTO){
        Item item = new Item();
        item.setFilePath(itemDTO.getFilePath());
        item.setItemName(itemDTO.getItemName());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        itemRepository.save(item);
    }

}
