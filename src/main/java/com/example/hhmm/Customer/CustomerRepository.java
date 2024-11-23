package com.example.hhmm.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String name); // 이건 내 아이디
    Optional<Customer> findByNickname(String nickname); // 이건 내 닉네임
}
