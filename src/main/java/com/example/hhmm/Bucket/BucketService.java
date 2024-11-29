package com.example.hhmm.Bucket;

import java.util.ArrayList;
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

    // bucketItemDTO.quantity와 itemDTO.id를 보내왔음
    public String buyBucketItem(List<BucketItemDTO> bucketItemDTOs, String nickname) {
        Customer customer = customerRepository.findByNickname(nickname)
                                                .orElseThrow(()-> new DataNotFoundException("Customer not found"));       
        
        int customerMoney = customer.getPayMoney();
        int totalPrice = 0;

        String result = null;

        Bucket bucket = customer.getBucket();
        List<BucketItem> bucketItem = bucket.getItemList();
        List<Item> itemList = new ArrayList<>();

        for (BucketItemDTO bucketItemDTO : bucketItemDTOs) {
            Optional<Item> item = itemRepository.findById(bucketItemDTO.getItemDTO().getItemId());
            if(item.isPresent() && item.get().getQuantity() >= bucketItemDTO.getQuantity()){
                totalPrice += item.get().getPrice() * bucketItemDTO.getQuantity();
                itemList.add(item.get());
            }else{
                return "해당 상품이 품절되었습니다. 상품명: " + item.get().getItemName();
            }
        }
        
        result = customerMoney >= totalPrice ? customer.payment(customerMoney, totalPrice) : "잔액이 부족합니다.";
        customer.setPayMoney(customerMoney - totalPrice);
        customerRepository.save(customer);  // 결제

        for(int i=0; i < bucketItemDTOs.size(); i++){
            BucketItemDTO bucketItemDTO = bucketItemDTOs.get(i);
            Item item = itemList.get(i);
            item.setQuantity(item.getQuantity() - bucketItemDTO.getQuantity());
            itemRepository.save(item); // 결제된 아이템 수량 빼기
        }

        for(int i = bucketItemDTOs.size(); i > 0; i--){
            bucket.removeBucket(bucketItem.get(i-1));
        }
        bucketRepository.save(bucket); // 결제한 장바구니 아이템 지우기

        return result;    // 결제가 가능하면 "결제가 가능합니다." 아니면 "잔액이 부족합니다."
    }
}
