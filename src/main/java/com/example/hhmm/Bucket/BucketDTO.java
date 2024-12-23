package com.example.hhmm.Bucket;

import java.util.ArrayList;
import java.util.List;

import com.example.hhmm.BucketItem.BucketItemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketDTO {

    private Long id;
    private List<BucketItemDTO> itemList = new ArrayList<>();
}
