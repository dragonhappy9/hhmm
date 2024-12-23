package com.example.hhmm.Bucket;

import java.util.stream.Collectors;

import com.example.hhmm.BucketItem.BucketItemMapper;

public class BucketMapper {
        public static BucketDTO toDTO(Bucket bucket){
        return new BucketDTO(
            bucket.getId(),
            bucket.getItemList().stream()
                .map(BucketItemMapper::toDTO)
                .collect(Collectors.toList())
        );
    }
}
