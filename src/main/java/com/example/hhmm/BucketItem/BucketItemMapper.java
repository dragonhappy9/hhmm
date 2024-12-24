package com.example.hhmm.BucketItem;

import com.example.hhmm.Bucket.Bucket;
import com.example.hhmm.Item.ItemMapper;

public class BucketItemMapper {

    public static BucketItem toEntity(BucketItemDTO bucketItemDTO){
        BucketItem bucketItem = new BucketItem();
        Bucket bucket = new Bucket();
        bucket.setId(bucketItemDTO.getBucketId());
        bucketItem.setId(bucketItemDTO.getId());
        bucketItem.setBucket(bucket);
        bucketItem.setQuantity(bucketItemDTO.getQuantity());
        return bucketItem;
    }

    public static BucketItemDTO toDTO(BucketItem bucketItem){
        return new BucketItemDTO(
            bucketItem.getId(),
            bucketItem.getBucket().getId(),
            ItemMapper.toDTO(bucketItem.getItem()),
            bucketItem.getQuantity()
        );
    }
}
