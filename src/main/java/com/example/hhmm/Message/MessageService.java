package com.example.hhmm.Message;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Exception.DataNotFoundException;
import com.example.hhmm.Customer.Customer;
import com.example.hhmm.Customer.CustomerRepository;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void sendMessage(MessageDTO messageDTO) {
        Customer receiver = customerRepository.findByNickname(messageDTO.getReceiver())
            .orElseThrow(()-> new DataNotFoundException("수신자 정보를 찾을 수 없습니다." + messageDTO.getReceiver()));
        Customer sender = customerRepository.findByNickname(messageDTO.getSender())
            .orElseThrow(()-> new DataNotFoundException("송신자 정보를 찾을 수 없습니다." + messageDTO.getSender()));

        Message message = new Message(receiver, sender, messageDTO.getTitle(), messageDTO.getContent());
        messageRepository.save(message);
    }

    @Transactional
    public MessageDTO getMessage(Long messageId){
        Message message = messageRepository.findById(messageId)
            .orElseThrow(()-> new DataNotFoundException("쪽지를 찾을 수 없습니다." + messageId));
        return MessageDTO.toDTO(message);
    }


    @Transactional(readOnly = true)
    public List<MessageDTO> receivedMessages(String nickname) {
        // 받은 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        Customer customer = customerRepository.findByNickname(nickname)
            .orElseThrow(()-> new DataNotFoundException("사용자 정보를 찾을 수 없습니다." + nickname));
        List<Message> messages = messageRepository.findAllByReceiver(customer);
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for(Message message : messages) {
            // 내가 삭제한 메세지가 아니라면 모두 표시
            if(!message.isDeletedByReceiver()) {
                messageDTOs.add(MessageDTO.toDTO(message));
            }
        }
        return messageDTOs;
    }

    @Transactional(readOnly = true)
    public List<MessageDTO> sendedMessages(String nickname) {
        // 보낸 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        Customer customer = customerRepository.findByNickname(nickname)
            .orElseThrow(()-> new DataNotFoundException("사용자 정보를 찾을 수 없습니다." + nickname));
        List<Message> messages = messageRepository.findAllBySender(customer);
        List<MessageDTO> messageDTOs = new ArrayList<>();

        for(Message message : messages) {
            // 내가 삭제한 메세지가 아니라면 모두 표시
            if(!message.isDeletedBySender()) {
                messageDTOs.add(MessageDTO.toDTO(message));
            }
        }
        return messageDTOs;
    }

    // 메시지 지우기(리스트)
    @Transactional
    public boolean deleteMessages(List<Long> messageIds, String nickname) {
        
        for (Long messageId : messageIds) {
            Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new DataNotFoundException("Message not found: " + messageId));

            // 보낸이가 접속한 이라면 보낸 메시지 삭제
            if(message.getSender().getNickname().equals(nickname)){
                message.deleteBySender();
            }
            // 받는이가 접속한 이라면 받는 메시지 삭제
            else if(message.getReceiver().getNickname().equals(nickname)){
                message.deleteByReceiver();
            }

            if (message.isDeleted()) {
                try{
                    messageRepository.delete(message);
                }catch (PersistenceException e) {
                    return false;
                }
            }
        } 
        return true;
    }

    // 메시지 지우기
    @Transactional
    public boolean deleteMessage(Long id, String nickname) {
        
        Message message = messageRepository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Message not found: " + id));

        if(message.getSender().getNickname().equals(nickname)) {
            message.deleteBySender();
        }
        else if(message.getReceiver().getNickname().equals(nickname)){
            message.deleteByReceiver();
        }

        if (message.isDeleted()) {
            try{
                messageRepository.delete(message);
            }catch(PersistenceException e) {
                return false;
            }
        }
        return true;
    }
}
