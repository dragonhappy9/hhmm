package com.example.hhmm.Bucket;

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

    public static BucketItemDTO toDTO(BucketItem bucketItem){
        return new BucketItemDTO(
            bucketItem.getId(),
            BucketDTO.toDTO(bucketItem.getBucket()),
            ItemDTO.toDTO(bucketItem.getItem()),
            bucketItem.getQuantity()
        );
    }
}
// public BucketItemDTO(BucketItem bucketItem, boolean includeBucket){
//     this.id = bucketItem.getId();
//     if (includeBucket) {
//         this.bucketDTO = new BucketDTO(bucketItem.getBucket());
//     }
//     this.itemDTO = new ItemDTO(bucketItem.getItem());
//     this.quantity = bucketItem.getQuantity();
// }