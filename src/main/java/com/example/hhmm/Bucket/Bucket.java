package com.example.hhmm.Bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketId;

    @OneToMany(mappedBy = "bucket", cascade = CascadeType.REMOVE)
    private List<BucketItem> itemList = new ArrayList<>();

    public Bucket(BucketDTO bucketDTO){
        this.bucketId = bucketDTO.getBucketId();
        this.itemList = bucketDTO.getItemList().stream().map(BucketItem::new).collect(Collectors.toList());
    }
}

