package com.example.hhmm.Message;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hhmm.Customer.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageApiController {
    
    private final MessageService messageService;

    @DeleteMapping("/api/message")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteReceivedMessage(
            @RequestBody List<Long> messageIds,
            @AuthenticationPrincipal CustomUserDetails userDetails){

        boolean success = messageService.deleteMessages(messageIds, userDetails.getNickname());

        if (success) {
            return ResponseEntity.ok("삭제 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("삭제 실패...");
        }
    }

    @DeleteMapping("/api/message/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteSendedMessage(@PathVariable("id") Long id, 
                                        @AuthenticationPrincipal CustomUserDetails userDetails){
        boolean success = messageService.deleteMessage(id, userDetails.getNickname());

        if (success) {
            return ResponseEntity.ok("삭제 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("삭제 실패...");
        }
    }
}
