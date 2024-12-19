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
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketId;

    // 하나의 Bucket은 여러 BucketItem을 가질 수 있음
    // Bucket이 삭제, 생성, BucketItem도 동일하게 적용
    // Bucket과 BucketItem의 관계가 끊어진다면 해당 BucketItem을 자동으로 삭제
    @OneToMany(mappedBy = "bucket", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<BucketItem> itemList = new ArrayList<>();
    
    public void addBucket(BucketItem bucketItem){
        // id가 동일한 객체가 존재하면 bucketItem의 quantity수량을 합치고 기존 아이템을 제거
        for(int i = 0; i < this.itemList.size(); i++){
            if(this.itemList.get(i).getId().equals(bucketItem.getId())){
                bucketItem.setQuantity(this.itemList.get(i).getQuantity() + bucketItem.getQuantity());
                this.itemList.remove(i);
            }
        }
        this.itemList.add(bucketItem);
        bucketItem.setBucket(this);
    }

    public void removeBucket(BucketItem bucketItem){
        this.itemList.remove(bucketItem);
        bucketItem.setBucket(null);
    }
}
    // public Bucket(BucketDTO bucketDTO){
    //     this.bucketId = bucketDTO.getBucketId();
    //     this.itemList = bucketDTO.getItemList().stream().map(BucketItem::new).collect(Collectors.toList());
    // }