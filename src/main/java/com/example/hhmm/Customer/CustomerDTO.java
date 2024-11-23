package com.example.hhmm.Customer;

import com.example.hhmm.Bucket.Bucket;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    @Size(min = 8, max = 20, message = "사용자ID는 최소 8글자 최대 20글자 입니다.")
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordChk;

    @Size(max = 20, message = "닉네임은 최대 20글자 입니다.")
    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;

    private boolean gender;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String home;

    private Bucket bucket;

    public CustomerDTO(Customer customer){
        this.name = customer.getName();
        this.password = customer.getPassword();
        this.nickname = customer.getNickname();
        this.email = customer.getEmail();
        this.gender = customer.isGender();
        this.home = customer.getHome();
        this.bucket = customer.getBucket();
    }
}
