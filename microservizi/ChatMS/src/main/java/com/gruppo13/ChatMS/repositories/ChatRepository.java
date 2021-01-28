package com.gruppo13.ChatMS.repositories;

import com.gruppo13.ChatMS.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
