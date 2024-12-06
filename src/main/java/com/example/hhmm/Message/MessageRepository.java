package com.example.hhmm.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hhmm.Customer.Customer;

public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findAllByReceiver(Customer customer);
    List<Message> findAllBySender(Customer customer);
}
