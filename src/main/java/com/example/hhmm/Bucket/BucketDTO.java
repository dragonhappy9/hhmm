package com.example.hhmm.Bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketDTO {

    private Long bucketId;

    private List<BucketItemDTO> itemList = new ArrayList<>();

    public static BucketDTO toDTO(Bucket bucket){
        return new BucketDTO(
            bucket.getBucketId(),
            bucket.getItemList().stream()
                .map(BucketItemDTO::toDTO)
                .collect(Collectors.toList())
        );
    }
}
