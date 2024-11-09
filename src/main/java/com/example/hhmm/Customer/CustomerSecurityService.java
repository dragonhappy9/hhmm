package com.example.hhmm.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerSecurityService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    // 로그인시 Authentication 에는 로그인폼에 입력한 사용자 정보가 저장이 되는데 나는 
    // 사용자Id(name), 비밀번호(password)를 저장했다. 
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 즉 여기서는 사용자Id 정보를 통해 고객 객체를 얻어내는 과정이다.
        Optional<Customer> _customer = this.customerRepository.findByName(name);
        if (_customer.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        CustomerDTO customerDTO = new CustomerDTO(_customer.get());
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 여기서는 사용자의 권한 정보를 포함하기위해 권한 설정을 하는 것이다.
        if("marketadmin".equals(name.trim())){
            authorities.add(new SimpleGrantedAuthority(CustomerRole.MARKETMANAGER.getValue()));
        }else if("postadmin".equals(name.trim())){
            authorities.add(new SimpleGrantedAuthority(CustomerRole.POSTMANAGER.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(CustomerRole.CUSTOMER.getValue()));
        }
        // 마지막으로 커스텀 유저객체에 고객의 아이디, 비밀번호와, 권한, 닉네임을 저장하여 반환한다.
        return new CustomUserDetails(customerDTO.getName(), customerDTO.getPassword(), authorities, customerDTO.getNickname());
    }
}
