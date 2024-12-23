package com.example.hhmm.Customer;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer){
        return new CustomerDTO(
            customer.getCId(), 
            customer.getCPw(), 
            null, 
            customer.getName(), 
            customer.getNickname(), 
            customer.isGender(), 
            customer.getHome()
        );
    }
}
