package com.example.demo.payload;

import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {




    private  String username;


    private Integer number;


    private  Double balance;

    private Date expired_date;

    private  boolean  isActive;


    private UUID userId;

}
