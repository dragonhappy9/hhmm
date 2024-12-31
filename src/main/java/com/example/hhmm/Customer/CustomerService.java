package com.example.hhmm.Customer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hhmm.Bucket.Bucket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public void create(CustomerDTO customerDTO){
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Bucket bucket = new Bucket(); 
        customer.setCPw(passwordEncoder.encode(customerDTO.getCPw()));  // 비밀번호를 암호화하여 저장
        customer.setBucket(bucket); // 사용자에게 장바구니 할당
        this.customerRepository.save(customer);
    }
}
