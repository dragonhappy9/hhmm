package com.example.hhmm.Customer;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    
    private String c_id;
    private String c_pw;
    private List<GrantedAuthority> authorities;
    private String nickname; // 닉네임 정보를 포함하기 위해 넣었다.
    private String name;

    public CustomUserDetails(String c_id, String c_pw, List<GrantedAuthority> authorities, String nickname, String name) {
        this.c_id = c_id;
        this.c_pw = c_pw;
        this.authorities = authorities;
        this.nickname = nickname;
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName(){
        return name;
    }

    // UserDetails 인터페이스 메서드 구현
    @Override
    public String getUsername() {
        return c_id;
    }

    @Override
    public String getPassword() {
        return c_pw;
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
