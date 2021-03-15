package com.gruppo13.ChatMS.repositories;

import com.gruppo13.ChatMS.model.Chat;
import com.gruppo13.ChatMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT u.chat FROM User u WHERE u.id = :id")
    public Set<Chat> getUserTagsByUserId(@Param("id") Long id);

    Optional<Chat> findByCourseId(Long courseId);

    @Query(value = "INSERT INTO user_chat(user_id, chat_id) VALUES (?2, ?1) RETURNING *", nativeQuery = true)
    Object addChatUser(Long chatId, Long userId);

    @Query(value = "DELETE FROM user_chat WHERE chat_id = ?1 AND user_id = ?2 RETURNING *", nativeQuery = true)
    Object deleteChatUser(Long chatId, Long userId);

}
