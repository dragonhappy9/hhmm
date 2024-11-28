package com.example.hhmm.Bucket;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hhmm.Customer.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;
    
    @GetMapping
    public String getBucketItemDTOs(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String customerNickname = userDetails.getNickname();    // 접속한 유저 정보 가져와서
        List<BucketItemDTO> bucketItemDTOs = bucketService.getBucketItemList(customerNickname);      // 접속한 유저의 장바구니를 가져와서
        model.addAttribute("bucketItemDTOs", bucketItemDTOs);   // 모델에 추가하고
        return "customer/bucket";  // 페이지 이동
    }
}
