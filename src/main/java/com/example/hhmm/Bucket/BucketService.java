package com.example.hhmm.Bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    @Transactional(readOnly = true)
    public List<BucketItemDTO> getBucketItemList(String customerNickname){
        Customer customer = customerRepository.findByNickname(customerNickname)
                                                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        BucketDTO bucketDTO = new BucketDTO(customer.getBucket()); 
        List<BucketItemDTO> bucketItemDTOs = bucketDTO.getItemList();
        return bucketItemDTOs;
    }

    // bucketItemDTO.quantity와 itemDTO.id를 보내왔음
    @Transactional
    public String buyBucketItem(List<BucketItemDTO> bucketItemDTOs, String nickname) {
        Customer customer = customerRepository.findByNickname(nickname)
                                                .orElseThrow(()-> new DataNotFoundException("Customer not found"));       
        
        int customerMoney = customer.getPayMoney();
        int totalPrice = 0;

        Bucket bucket = customer.getBucket();
        List<BucketItem> bucketItem = bucket.getItemList();
        List<Item> itemList = new ArrayList<>();

        for (BucketItemDTO bucketItemDTO : bucketItemDTOs) {
            // 공유락을 통해 아이템 수량을 확인하여 결제 여부를 구분
            Item item = itemRepository.findWithSharedLock(bucketItemDTO.getItemDTO().getItemId())
                .orElseThrow(() -> new DataNotFoundException("Item not found"));
            
            // 현재 남은 수량이 담은 수량보다 클 때
            if(item.getQuantity() >= bucketItemDTO.getQuantity()){
                totalPrice += item.getPrice() * bucketItemDTO.getQuantity();
                itemList.add(item);
            }else{
                return "해당 상품의 남은 수량이 구매하려는 수량보다 적습니다! \n 상품명: " + item.getItemName() + "\n남은 개수: " + item.getQuantity();
            }
        }
        
        if (customerMoney < totalPrice) {
            return "잔액이 부족합니다.";
        }

        customer.setPayMoney(customerMoney - totalPrice);
        customerRepository.save(customer);  // 결제

        for(int i = 0; i < bucketItemDTOs.size(); i++){
            BucketItemDTO bucketItemDTO = bucketItemDTOs.get(i);
            // 독점락 걸어서 결제 완료까지 업데이트, 삭제 막기
            Item lockedItem = itemRepository.findWithExclusiveLock(itemList.get(i).getItemId())
                                        .orElseThrow(() -> new DataNotFoundException("Item not found"));

            lockedItem.setQuantity(lockedItem.getQuantity() - bucketItemDTO.getQuantity());
            itemRepository.save(lockedItem); // 결제된 아이템 수량 빼기
        }

        for(int i = bucketItemDTOs.size(); i > 0; i--){
            bucket.removeBucket(bucketItem.get(i - 1));
        }
        bucketRepository.save(bucket); // 결제한 장바구니 아이템 지우기

        return "결제가 완료되었습니다.";
    }
}
