package com.example.hhmm.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setCId(customerDTO.getCId());
        customer.setCPw(customerDTO.getCPw());
        customer.setName(customerDTO.getName());
        customer.setNickname(customerDTO.getNickname());
        customer.setEmail(customerDTO.getEmail());
        customer.setHome(customerDTO.getHome());
        customer.setGender(customer.isGender());
        return customer;
    }

    public static CustomerDTO toDTO(Customer customer){
        return new CustomerDTO(
            customer.getCId(), 
            customer.getCPw(), 
            null, 
            customer.getName(), 
            customer.getNickname(),
            customer.getEmail(),
            customer.getHome(),
            customer.isGender() 
        );
    }
}
