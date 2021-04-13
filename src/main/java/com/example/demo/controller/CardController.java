package com.example.demo.controller;


import com.example.demo.payload.ApiResponce;
import com.example.demo.payload.CardDto;
import com.example.demo.payload.TransferDto;
import com.example.demo.service.CardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    @Qualifier("cardService")
    CardService cardService;


    @PostMapping("/transfer")
    public HttpEntity<?> transfer(@RequestBody TransferDto transferDto){

        ApiResponce transfer = cardService.transfer(transferDto);

        return ResponseEntity.status(transfer.isSuccess()?200:409).body(transfer);


    }

    @PostMapping("/addcard")
    public HttpEntity<?> addCard(@RequestBody CardDto cardDto){

        ApiResponce apiResponce = cardService.addCard(cardDto);

        return ResponseEntity.status(apiResponce.isSuccess()?200:409).body(apiResponce);
    }

    @PostMapping("/cardhistory")
    public HttpEntity<?> cardHistory(@RequestParam Integer cardId){

        ApiResponce apiResponce = cardService.cardHistory(cardId);

        return ResponseEntity.status(apiResponce.isSuccess()?200:409).body(apiResponce);
    }


}
