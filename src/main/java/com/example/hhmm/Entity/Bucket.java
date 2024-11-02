package com.example.hhmm.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "myBucket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;      // Customer 안에 포함되었기 때문에 회원탈퇴시 
                                    // 회원의 Bucket을 삭제하도록 설정
    @ManyToMany
    @JoinTable(                     // 테이블을 조인하여 연결 테이블을 만들었고,  
        name = "bucketItem",        // 여러개의 Item이 여러개의 Bucket 안에 포함될 수 있도록 하였습니다.
        joinColumns = @JoinColumn(name = "bucketId"), 
        inverseJoinColumns = @JoinColumn(name = "itemId") 
    )

    private List<Item> itemList = new ArrayList<>();

    public void addItem(Item item){
        itemList.add(item);
    }

    public void removeItem(Item item){
        itemList.remove(item);
    }
}
