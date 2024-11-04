package com.example.hhmm.Customer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    
    public void create(CustomerDTO customerDTO){
        Customer customer = new Customer(customerDTO);
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));  // 비밀번호를 암호화하여 저장
        this.customerRepository.save(customer);
    }
}
