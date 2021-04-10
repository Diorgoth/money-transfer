package com.example.demo.repository;

import com.example.demo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card,Integer> {

boolean existsByIdAndUserId(Integer id, UUID user_id);

boolean existsById(Integer id);

}
