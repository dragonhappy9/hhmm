package com.example.hhmm.Message;

import com.example.hhmm.Customer.Customer;

public class MessageMapper {
    
    public static Message toEntity(MessageDTO messageDTO){
        Message message = new Message();
        Customer sender = new Customer();
        Customer receiver = new Customer();
        sender.setNickname(messageDTO.getSender());
        receiver.setNickname(messageDTO.getReceiver());
        message.setId(messageDTO.getId());
        message.setTitle(messageDTO.getTitle());
        message.setContent(messageDTO.getContent());
        message.setSender(sender);
        message.setReceiver(receiver);
        return message;
    }

    public static MessageDTO toDTO(Message message){
        return new MessageDTO(
            message.getId(),
            message.getTitle(),
            message.getContent(),
            message.getSender().getNickname(),
            message.getReceiver().getNickname()
        );
    }
}
