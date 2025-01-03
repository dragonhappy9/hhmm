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

    // 로그인시 Authentication 에는 로그인폼에 입력한 사용자 정보가 저장이 됩니다.
    // Username -> c_id(내가사용한 유저아이디), password -> c_pw(내가사용한 유저비밀번호) 
    @Override
    public UserDetails loadUserByUsername(String c_id) throws UsernameNotFoundException {
        Optional<Customer> _customer = this.customerRepository.findBycId(c_id);
        if (_customer.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        CustomerDTO customerDTO = CustomerMapper.toDTO(_customer.get());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admini123".equals(c_id.trim())){ // 권한설정
            authorities.add(new SimpleGrantedAuthority(CustomerRole.MARKETMANAGER.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(CustomerRole.CUSTOMER.getValue()));
        }
        // 마지막으로 커스텀 유저객체에 고객의 아이디, 비밀번호와, 권한, 별명, 이름을 저장하여 반환한다.
        return new CustomUserDetails(customerDTO.getCId(), customerDTO.getCPw(), authorities, customerDTO.getNickname(), customerDTO.getName());
    }
}
