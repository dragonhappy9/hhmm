package com.example.hhmm.Bucket;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/api/bucket")
@RequiredArgsConstructor
public class BucketApiController {
    
    private final BucketService bucketService;

    @PostMapping("/{itemId}/add")
    public ResponseEntity<?> addToBucket(@RequestBody Map<String, String> requestData) {
        Long itemId = Long.parseLong(requestData.get("itemId"));
        String customerNickname = requestData.get("customerNickname");
        int quantity = Integer.parseInt(requestData.get("quantity"));
        boolean success = bucketService.addItemToCustomerBucket(itemId, customerNickname, quantity);
        if (success) {
            return ResponseEntity.ok("장바구니에 담았습니다!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("장바구니에 담지 못했습니다...");
        }
    }
}
