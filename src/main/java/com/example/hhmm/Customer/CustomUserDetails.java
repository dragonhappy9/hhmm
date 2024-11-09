package com.example.hhmm.Customer;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private String nickname; // 닉네임 정보를 포함하기 위해 넣었다.

    public CustomUserDetails(String username, String password, List<GrantedAuthority> authorities, String nickname) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    // UserDetails 인터페이스 메서드 구현
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
