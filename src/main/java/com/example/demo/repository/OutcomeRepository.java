package com.example.demo.repository;

import com.example.demo.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome,Integer> {

   List<Outcome> findByFromCard_Id(Integer fromCard_id);

}
