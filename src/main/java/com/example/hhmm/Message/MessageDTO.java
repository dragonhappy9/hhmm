package com.example.hhmm.Message;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;

    @NotBlank(message="제목은 필수항목입니다.")
    private String title;

    @NotBlank(message="내용은 필수항목입니다.")
    private String content;

    @NotBlank(message="보내는이가 선택되지 않았습니다.")
    private String sender;

    @NotBlank(message="받는이가 선택되지 않았습니다.")
    private String receiver;

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
