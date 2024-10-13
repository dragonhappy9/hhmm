package com.example.hhmm.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Customer {

    @Id
    @Column(length = 30, nullable = false)
    private String id;

    @Column(length = 60, nullable = false)
    private String pw;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean gender;

    @Column(length = 50, nullable = false)
    private String home;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MyBucket myBucket;

}
