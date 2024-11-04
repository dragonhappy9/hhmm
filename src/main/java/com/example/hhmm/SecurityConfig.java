package com.example.hhmm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  // 스프링 환경설정 파일임을 명시하는 어노테이션
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인증되지 않은 모든 페이지의 요청을 허락
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            // .csrf((csrf) -> csrf
            //     .ignoringRequestMatchers(
            //         new AntPathRequestMatcher("/some-endpoint/**") // 예외 처리할 경로 설정
            //     ))
            .formLogin((formLogin) -> formLogin
                .loginPage("/customer/login")
                .defaultSuccessUrl("/post/posts")
                .usernameParameter("name") // username파라미터를 "name"으로 변경
                .passwordParameter("password")) // 위와 동일
            .logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/customer/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true))     
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}