package com.example.demo.repository;

import com.example.demo.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {
}
