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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String name;     // 아이디와 닉네임은 unique 속성으로 무결성 유지

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean gender;

    @Column(length = 50, nullable = false)
    private String home;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "bucket_id")
    private Bucket bucket;      // 하나의 Customer는 하나의 Bucket을 가지는 관계

    @Column(nullable = true)
    private int payMoney;

    @PrePersist
    protected void onCreate(){
        this.payMoney = 0;
    }

    public Customer(CustomerDTO CustomerDTO) {
        this.name = CustomerDTO.getName();
        this.password = CustomerDTO.getPassword();
        this.nickname = CustomerDTO.getNickname();
        this.email = CustomerDTO.getEmail();
        this.gender = CustomerDTO.isGender();
        this.home = CustomerDTO.getHome();
        this.bucket = CustomerDTO.getBucket();
        this.payMoney = CustomerDTO.getPayMoney();
    }
}
