package com.example.hhmm.Bucket;

import com.example.hhmm.Item.Item;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class BucketItem {
    
    @EmbeddedId
    private BucketItemId id; // 복합 키

    @ManyToOne (fetch = FetchType.LAZY)
    @MapsId("bucketId")
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    @ManyToOne (fetch = FetchType.LAZY)
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    @Min(0)
    private int quantity;

    public BucketItem(Bucket bucket, Item item, int quantity) {
        this.bucket = bucket;
        this.item = item;
        this.quantity = quantity;
        this.id = new BucketItemId(bucket.getBucketId(), item.getItemId());
    }

    public BucketItem(BucketItemDTO bucketItemDTO) {
        this.bucket = new Bucket(bucketItemDTO.getBucketDTO());
        this.item = new Item(bucketItemDTO.getItemDTO());
        this.quantity = bucketItemDTO.getQuantity();
        this.id = new BucketItemId(bucket.getBucketId(), item.getItemId());
    }
}
