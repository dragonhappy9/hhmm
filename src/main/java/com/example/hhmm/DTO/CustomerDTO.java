package com.example.hhmm.DTO;

import com.example.hhmm.Entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private String cId;

    private String cPw;

    private String name;

    private String nickname;

    private boolean gender;

    private String home;

    public CustomerDTO(Customer customer){
        this.cId = customer.getCId();
        this.cPw = customer.getCPw();
        this.name = customer.getName();
        this.nickname = customer.getNickname();
        this.gender = customer.isGender();
        this.home = customer.getHome();
    }
}
