package com.example.hhmm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String cId;

    @Column(length = 60, nullable = false)
    private String cPw;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private boolean gender;

    @Column(length = 50, nullable = false)
    private String home;

    @OneToOne
    @JoinColumn(name = "bucketId")
    private MyBucket myBucket;

}
