package com.example.demo.repository;

import com.example.demo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

boolean existsByIdAndUserId(Integer id, UUID user_id);
boolean existsByIdAndUser_Email(Integer id, String user_email);

boolean existsById(Integer id);



}
