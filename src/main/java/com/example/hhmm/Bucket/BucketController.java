package com.example.hhmm.Bucket;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hhmm.Customer.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getBucketItemDTOs(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String customerNickname = userDetails.getNickname();    // 접속한 유저 정보 가져와서
        List<BucketItemDTO> bucketItemDTOs = bucketService.getBucketItemList(customerNickname);      // 접속한 유저의 장바구니를 가져와서
        model.addAttribute("bucketItemDTOs", bucketItemDTOs);   // 모델에 추가하고
        return "customer/bucket";  // 페이지 이동
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/buy")
    public String buyBucketItem(@ModelAttribute BucketItemForm bucketItemForm, 
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                RedirectAttributes redirectAttributes){

        List<BucketItemDTO> bucketItemDTOs = bucketItemForm.getBucketItemDTOs();
        String result = bucketService.buyBucketItem(bucketItemDTOs, userDetails.getNickname());
        if(result.equals("결제가 가능합니다.")){
            redirectAttributes.addFlashAttribute("message", "결제 완료");
        }else if (result.equals("잔액이 부족합니다.")){
            redirectAttributes.addFlashAttribute("message", "잔액 부족");
        }else{
            redirectAttributes.addFlashAttribute("message", result);
        }
        return "redirect:/bucket";
    }
}
