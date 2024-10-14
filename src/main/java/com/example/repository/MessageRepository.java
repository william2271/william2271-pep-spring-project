package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import com.example.entity.*;
public interface MessageRepository extends JpaRepository<Message, Integer> {
Optional<Message> findById(Integer messageId );
List<Message> findByPostedBy(Integer postedBy );
}
