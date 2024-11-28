package com.example.hhmm.Bucket;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.Customer.Customer;
import com.example.hhmm.Customer.CustomerRepository;
import com.example.hhmm.Item.Item;
import com.example.hhmm.Item.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BucketService { 
    
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final BucketRepository bucketRepository;

    public boolean addItemToCustomerBucket(Long itemId, String customerNickname, int quantity){
        Optional<Item> _item = itemRepository.findById(itemId);
        Optional<Customer> _customer = customerRepository.findByNickname(customerNickname);
        if(_customer.isPresent() && _item.isPresent()){
            Bucket bucket = _customer.get().getBucket();    // item을 저장할 장바구니
            Item item = _item.get();                        // 저장할 item
            BucketItem bucketItem = new BucketItem(bucket, item, quantity);    // 장바구니, item, 담을 item수량
            bucket.addBucket(bucketItem);                   // 장바구니에 담기
            bucketRepository.save(bucket);                  // 저장
            return true;
        }else{
            throw new DataNotFoundException("고객 혹은 상품 정보를 찾지 못했습니다.");
        }
    }

    public List<BucketItemDTO> getBucketItemList(String customerNickname){
        Customer customer = customerRepository.findByNickname(customerNickname)
                                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        BucketDTO bucketDTO = new BucketDTO(customer.getBucket()); 
        List<BucketItemDTO> bucketItemDTOs = bucketDTO.getItemList();
        return bucketItemDTOs;
    }
}
