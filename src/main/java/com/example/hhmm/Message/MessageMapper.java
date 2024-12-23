package com.example.hhmm.Message;

public class MessageMapper {
    public static MessageDTO toDTO(Message message){
        return new MessageDTO(
            message.getTitle(),
            message.getContent(),
            message.getSender().getNickname(),
            message.getReceiver().getNickname()
        );
    }
}
