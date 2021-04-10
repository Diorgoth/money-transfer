package com.example.demo.controller;

import com.example.demo.entity.Income;
import com.example.demo.payload.ApiResponce;
import com.example.demo.payload.CardDto;
import com.example.demo.payload.TransferDto;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
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


}
