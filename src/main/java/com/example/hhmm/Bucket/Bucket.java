package com.example.hhmm.Bucket;

import java.util.ArrayList;
import java.util.List;

import com.example.hhmm.Item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany
    @JoinTable(                     // 테이블을 조인하여 연결 테이블을 만들었고,  
        name = "bucket_item",        // 여러개의 Item이 여러개의 Bucket 안에 포함될 수 있도록 하였습니다.
        joinColumns = @JoinColumn(name = "bucket_id"), 
        inverseJoinColumns = @JoinColumn(name = "item_id") 
    )

    private List<Item> itemList = new ArrayList<>();
}

//     public void addItem(Item item){
//         itemList.add(item);
//     }

//     public void removeItem(Item item){
//         itemList.remove(item);
//     }
// }
