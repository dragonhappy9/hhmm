package com.example.hhmm.Customer;

import com.example.hhmm.Bucket.Bucket;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String cId; // 아이디와 

    @Column(length = 60, nullable = false)
    private String cPw;

    @Column(length = 20, nullable = false, unique = true)
    private String name; // 이름과

    @Column(length = 20, nullable = false, unique = true)
    private String nickname; // 닉네임은 unique 속성으로 무결성 유지

    @Column(length = 50, nullable = false)
    private String home;

    @Column(nullable = false)
    private boolean gender;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "bucket_id")
    private Bucket bucket; // 하나의 Customer는 하나의 Bucket을 가지는 관계

    @Column(nullable = true)
    private int payMoney;

    @PrePersist
    protected void onCreate(){
        this.payMoney = 0;
    }
}
