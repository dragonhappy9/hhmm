package com.example.hhmm.Customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
                    
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {

    @Size(min = 8, max = 20, message = "아이디는 최소 8글자 최대 20글자 입니다.")
    @NotEmpty(message = "아이디는 필수항목입니다.")
    private String c_id;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String c_pw;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String passwordChk;

    @Size(max = 20, message = "성함은 최대 20글자 입니다.")
    @NotEmpty(message = "성함은 필수항목입니다.")
    private String name;

    @Size(max = 20, message = "닉네임은 최대 20글자 입니다.")
    @NotEmpty(message = "닉네임은 필수항목입니다.")
    private String nickname;
    private boolean gender;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String home;
}
