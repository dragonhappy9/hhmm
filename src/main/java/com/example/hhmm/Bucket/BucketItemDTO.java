package com.example.hhmm.Bucket;

import com.example.hhmm.Item.ItemDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BucketItemDTO {

    private BucketItemId id;

    private BucketDTO bucketDTO;

    private ItemDTO itemDTO;

    private int quantity;

    public BucketItemDTO(BucketItem bucketItem, boolean includeBucket){
        this.id = bucketItem.getId();
        if (includeBucket) {
            this.bucketDTO = new BucketDTO(bucketItem.getBucket());
        }
        this.itemDTO = new ItemDTO(bucketItem.getItem());
        this.quantity = bucketItem.getQuantity();
    }
}
