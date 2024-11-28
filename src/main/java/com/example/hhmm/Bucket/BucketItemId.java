package com.example.hhmm.Bucket;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BucketItemId implements Serializable {

    private Long bucketId;
    private Long itemId;

    @Override
    public String toString() {
        return bucketId + "-" + itemId;
    }
}
