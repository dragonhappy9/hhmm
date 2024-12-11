package com.example.hhmm.Item;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
    
    private final ItemRepository itemRepository;
    private final ItemLogRepository itemLogRepository;

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

    @Transactional
    public List<ItemLogDTO> getTodayItemLog(){
        List<ItemLog> todayItemLog = itemLogRepository.findRankedBySoldDate(LocalDate.now());
        List<ItemLogDTO> todayItemLogDTO = todayItemLog.stream()
            .map(itemLog -> ItemLogDTO.toDTO(itemLog))
            .collect(Collectors.toList());
        return todayItemLogDTO;
    }

    @Transactional
    public List<ItemLogDTO> getYesterdayItemLog(){
        List<ItemLog> yesterdayItemLog = itemLogRepository.findRankedBySoldDate(LocalDate.now().minusDays(1));
        List<ItemLogDTO> yesterdayItemLogDTO = yesterdayItemLog.stream()
            .map(itemLog -> ItemLogDTO.toDTO(itemLog))
            .collect(Collectors.toList());
        return yesterdayItemLogDTO;
    }
}
