package com.example.hhmm.BucketItem;

import com.example.hhmm.Bucket.BucketMapper;
import com.example.hhmm.Item.ItemMapper;

public class BucketItemMapper {
        public static BucketItemDTO toDTO(BucketItem bucketItem){
        return new BucketItemDTO(
            bucketItem.getId(),
            BucketMapper.toDTO(bucketItem.getBucket()),
            ItemMapper.toDTO(bucketItem.getItem()),
            bucketItem.getQuantity()
        );
    }
}
