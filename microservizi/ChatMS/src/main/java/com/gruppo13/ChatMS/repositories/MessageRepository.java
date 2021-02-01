package com.gruppo13.ChatMS.repositories;

import com.gruppo13.ChatMS.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
