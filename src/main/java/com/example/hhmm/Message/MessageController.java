package com.example.hhmm.Message;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hhmm.Customer.CustomUserDetails;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequiredArgsConstructor
@Controller
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String messages(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        List<MessageDTO> receivedMessageDTOs = messageService.receivedMessages(userDetails.getNickname());
        List<MessageDTO> sendedMessageDTOs = messageService.sendedMessages(userDetails.getNickname());

        model.addAttribute("receivedMessageDTOs", receivedMessageDTOs);
        model.addAttribute("sendedMessageDTOs", sendedMessageDTOs);
        return "message/messages";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String sendForm(MessageDTO messageDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        messageDTO.setSender(userDetails.getNickname());
        return "message/message_create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String messageSend(@Valid MessageDTO messageDTO, BindingResult bindingResult, 
                                RedirectAttributes redirectAttributes, 
                                @AuthenticationPrincipal CustomUserDetails userDetails){
        if (bindingResult.hasErrors()) {
            return "message/message_create";
        }
        messageService.sendMessage(messageDTO);
        redirectAttributes.addFlashAttribute("message", "message 전송 성공");
        return "redirect:/message";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String messageDetail(Model model, @PathVariable("id") Long id, 
                                @AuthenticationPrincipal CustomUserDetails userDetails){
        MessageDTO messageDTO = messageService.getMessage(id);
        model.addAttribute("messageDTO", messageDTO);
        return "message/message_detail";
    }
}
