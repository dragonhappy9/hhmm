package com.example.hhmm.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerSecurityService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Customer> _customer = this.customerRepository.findByName(name);
        if (_customer.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        CustomerDTO customerDTO = new CustomerDTO(_customer.get());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("mm".equals(name)){
            authorities.add(new SimpleGrantedAuthority(CustomerRole.MARKETMANAGER.getValue()));
        }else if("pm".equals(name)){
            authorities.add(new SimpleGrantedAuthority(CustomerRole.POSTMANAGER.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(CustomerRole.CUSTOMER.getValue()));
        }
        return new User(customerDTO.getName(), customerDTO.getPassword(), authorities);
    }
}
