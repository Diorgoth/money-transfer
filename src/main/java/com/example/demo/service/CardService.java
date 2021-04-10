package com.example.demo.service;

import com.example.demo.entity.Card;
import com.example.demo.entity.Income;
import com.example.demo.entity.Outcome;
import com.example.demo.entity.User;
import com.example.demo.payload.ApiResponce;
import com.example.demo.payload.CardDto;
import com.example.demo.payload.TransferDto;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.IncomeRepository;
import com.example.demo.repository.OutcomeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OutcomeRepository outcomeRepository;




    public ApiResponce addCard(CardDto cardDto){


        Card card = new Card();

        card.setActive(cardDto.isActive());
        card.setBalance(cardDto.getBalance());
        card.setExpired_date(cardDto.getExpired_date());
        card.setNumber(cardDto.getNumber());
        card.setUsername(cardDto.getUsername());

        Optional<User> optionalUser = userRepository.findById(cardDto.getUserId());

        if (!optionalUser.isPresent()){
            return new ApiResponce("Such user not found",false);
        }

        card.setUser(optionalUser.get());

        cardRepository.save(card);

        return new ApiResponce("Card added",true);


    }



    public ApiResponce transfer(TransferDto transferDto){


        boolean existsByIdAndUserId = cardRepository.existsByIdAndUserId(transferDto.getSenderCardId(), transferDto.getUserId());

        if (!existsByIdAndUserId){
            return new ApiResponce("Such sender with this card not found",false);
        }

        boolean existsById = cardRepository.existsById(transferDto.getRecipientCardId());
        if (!existsById){
            return new ApiResponce("Such receiver card not found",false);
        }

        Optional<Card> optionalCard = cardRepository.findById(transferDto.getSenderCardId());

        Card sendercard = optionalCard.get();

        Double balance = sendercard.getBalance();

        Double commision =transferDto.getAmount()*0.05;
        Double commitionwithBalance = transferDto.getAmount()*1.05;

        if (commitionwithBalance> balance){
            return new ApiResponce("Not enough money",false);
        }

        Double remainingbalance = balance - commitionwithBalance;

        sendercard.setBalance(remainingbalance);
        Optional<Card> optionalCard1 = cardRepository.findById(transferDto.getRecipientCardId());
        Card recepientcard = optionalCard1.get();
        Double balance1 = recepientcard.getBalance();
        Double editedBalance = balance1 + transferDto.getAmount();
        recepientcard.setBalance(editedBalance);



        Income income = new Income();
        income.setFrom_card_id(cardRepository.getOne(transferDto.getSenderCardId()));
        income.setTo_card_id(cardRepository.getOne(transferDto.getRecipientCardId()));
        income.setAmount(transferDto.getAmount());
        incomeRepository.save(income);


        Outcome outcome = new Outcome();
        outcome.setFrom_card_id(cardRepository.getOne(transferDto.getSenderCardId()));
        outcome.setTo_card_id(cardRepository.getOne(transferDto.getRecipientCardId()));
        outcome.setCommision(commision);
        outcome.setAmount(transferDto.getAmount());
        Outcome save = outcomeRepository.save(outcome);
        return new ApiResponce("Transfer successfully processed", true,save);//foydaluvchi oziga tegishli card tarixi abject sifatida boradi

    }




}
