package com.example.hhmm.Bucket;

import java.util.stream.Collectors;

import com.example.hhmm.BucketItem.BucketItemMapper;

public class BucketMapper {

    public static Bucket toEntity(BucketDTO bucketDTO){
        Bucket bucket = new Bucket();
        bucket.setId(bucketDTO.getId());
        bucket.setItemList(
            bucketDTO.getItemList().stream()
                .map(BucketItemMapper::toEntity)
                .collect(Collectors.toList()));
        return bucket;
    }

    public static BucketDTO toDTO(Bucket bucket){
        return new BucketDTO(
            bucket.getId(),
            bucket.getItemList().stream()
                .map(BucketItemMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
}
