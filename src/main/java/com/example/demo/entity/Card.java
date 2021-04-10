package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private  String username;

    @Column(nullable = false,unique = true)
    private Integer number;

    @Column(nullable = false)
    private  Double balance;

    private Date expired_date;

    private  boolean  isActive;

    @OneToOne
    private User user;


}
