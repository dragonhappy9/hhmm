package com.example.hhmm.DTO;

import lombok.Data;

@Data
public class CustomerDTO {
    
    private Long id;
    
    private String c_id;

    private String c_pw;
    
    private String name;

    private String nickname;

    private boolean gender;

    private String home;
}
