package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    Double amount;
    UUID userId;
    Integer senderCardId;
    Integer recipientCardId;

}
