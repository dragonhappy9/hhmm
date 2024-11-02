package com.example.hhmm.Entity;

import com.example.hhmm.DTO.CustomerDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

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
    private String cId;     // 아이디와 닉네임은 unique 속성으로 무결성 유지

    @Column(length = 60, nullable = false)
    private String cPw;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private boolean gender;

    @Column(length = 50, nullable = false)
    private String home;

    @OneToOne
    @JoinColumn(name = "bucketId")
    private Bucket Bucket;      // 하나의 Customer는 하나의 Bucket을 가지는 관계

    public Customer(CustomerDTO CustomerDTO) {
        this.cId = CustomerDTO.getCId();
        this.cPw = CustomerDTO.getCPw();
        this.name = CustomerDTO.getName();
        this.nickname = CustomerDTO.getNickname();
        this.gender = CustomerDTO.isGender();
        this.home = CustomerDTO.getHome();
    }
}
