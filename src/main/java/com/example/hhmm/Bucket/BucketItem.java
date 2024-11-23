package com.example.hhmm.Bucket;

import com.example.hhmm.Item.Item;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BucketItem {
    
    @EmbeddedId
    private BucketItemId id; // 복합 키

    @ManyToOne
    @MapsId("bucketId")
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @JoinColumn(name = "quantity")
    private int quantity;

    public BucketItem() {}

    public BucketItem(Bucket bucket, Item item, int quantity) {
        this.bucket = bucket;
        this.item = item;
        this.quantity = quantity;
        this.id = new BucketItemId(bucket.getBucketId(), item.getItemId());
    }

    public BucketItem(BucketItemDTO bucketItemDTO) {
        this.bucket = bucketItemDTO.getBucket();
        this.item = bucketItemDTO.getItem();
        this.quantity = bucketItemDTO.getQuantity();
        this.id = new BucketItemId(bucket.getBucketId(), item.getItemId());
    }
}
