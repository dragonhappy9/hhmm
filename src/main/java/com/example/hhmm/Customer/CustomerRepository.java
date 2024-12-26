package com.example.hhmm.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCId(String c_id); // 아이디로 찾기
    Optional<Customer> findByName(String name); // 이름으로 찾기
    Optional<Customer> findByNickname(String nickname); // 별명으로 찾기
}
