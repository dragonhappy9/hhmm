package com.example.hhmm.Bucket;

import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public boolean addItemToCustomerBucket(Long itemId, String customerNickname){
        Optional<Item> item = itemRepository.findById(itemId);
        Optional<Customer> customer = customerRepository.findByNickname(customerNickname);
        if(customer.isPresent() && item.isPresent()){
            Bucket bucket = customer.get().getBucket();
            BucketItem bucketItem = new BucketItem(bucket, item.get(), 1);
            bucketRepository.save(bucketItem);
            return true;
        }else{
            new RuntimeException("고객 혹은 상품 정보를 찾지 못했습니다.");
        }
        return false;
    }
}
