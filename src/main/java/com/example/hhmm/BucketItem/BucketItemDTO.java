package com.example.hhmm.BucketItem;

import com.example.hhmm.Bucket.BucketDTO;
import com.example.hhmm.Item.ItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketItemDTO {

    private BucketItemId id;
    private BucketDTO bucketDTO;
    private ItemDTO itemDTO;
    private int quantity;
}
// public BucketItemDTO(BucketItem bucketItem, boolean includeBucket){
//     this.id = bucketItem.getId();
//     if (includeBucket) {
//         this.bucketDTO = new BucketDTO(bucketItem.getBucket());
//     }
//     this.itemDTO = new ItemDTO(bucketItem.getItem());
//     this.quantity = bucketItem.getQuantity();
// }