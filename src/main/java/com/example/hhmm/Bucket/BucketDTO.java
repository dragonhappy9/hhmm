package com.example.hhmm.Bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketDTO {

    private Long bucketId;

    private List<BucketItemDTO> itemList = new ArrayList<>();

    public BucketDTO(Bucket bucket){
        this.bucketId = bucket.getBucketId();
        this.itemList = bucket.getItemList().stream().map(BucketItemDTO::new).collect(Collectors.toList());
    }
}
