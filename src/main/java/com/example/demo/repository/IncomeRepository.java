package com.example.demo.repository;

import com.example.demo.entity.Card;
import com.example.demo.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income,Integer> {

List<Income> findByToCard_Id(Integer toCard_id);
}
