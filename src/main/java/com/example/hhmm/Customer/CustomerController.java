package com.example.hhmm.Customer;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

     @GetMapping("/signup")
    public String signup(CustomerDTO customerDTO) {
        return "customer/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "customer/signup";
        }

        if (!customerDTO.getPassword().equals(customerDTO.getPasswordChk())) {
            bindingResult.rejectValue("passwordChk", "passwordInCorrect", 
                    "패스워드가 일치하지 않습니다.");
            return "customer/signup";
        }

        try {
            customerService.create(customerDTO);
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "customer/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "customer/signup";
        }

        return "redirect:/posts";
    }

    @GetMapping("/login")
    public String login() {
        return "customer/login";
    } 
    
}
