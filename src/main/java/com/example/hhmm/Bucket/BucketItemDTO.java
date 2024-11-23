package com.example.hhmm.Bucket;

import com.example.hhmm.Item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketItemDTO {

    private BucketItemId id;

    private Bucket bucket;

    private Item item;

    private int quantity;

    public BucketItemDTO(BucketItem bucketItem){
        this.id = bucketItem.getId();
        this.bucket = bucketItem.getBucket();
        this.item = bucketItem.getItem();
        this.quantity = bucketItem.getQuantity();
    }
}
