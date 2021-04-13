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
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Autowired
    AuthService authService;

    User userDetails = (User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
    String username = userDetails.getUsername();



    public ApiResponce addCard(CardDto cardDto){


        Card card = new Card();

        card.setActive(cardDto.isActive());
        card.setBalance(cardDto.getBalance());
        card.setExpired_date(cardDto.getExpired_date());
        card.setNumber(cardDto.getNumber());
        card.setUsername(username);

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

        boolean cardidAndUsername = cardRepository.existsByIdAndUser_Email(transferDto.getSenderCardId(), username);//userning usernamei sifatida email alingan
//sendercard ning id si orqali olingan useremailni tokendagi username email bilan tekshrildi
        if (!cardidAndUsername){
            return new ApiResponce("Bu Userda bunday card yoq",false);//CARD shu user ga tegishli ekanligi tekshirilsin.
        }

        boolean existsByIdRecepient = cardRepository.existsById(transferDto.getRecipientCardId());
        if (!existsByIdRecepient){
            return new ApiResponce("Such receiver card not found",false);
        }

        Optional<Card> optionalCardSender = cardRepository.findById(transferDto.getSenderCardId());

        Card sendercard = optionalCardSender.get();

        Double balance = sendercard.getBalance();

        Double commision =transferDto.getAmount()*0.05;
        Double commitionwithBalance = transferDto.getAmount()*1.05;

        if (commitionwithBalance> balance){
            return new ApiResponce("Not enough money",false);
        }

        Double remainingbalance = balance - commitionwithBalance;

        sendercard.setBalance(remainingbalance);
        Optional<Card> optionalCardRecepient = cardRepository.findById(transferDto.getRecipientCardId());
        Card recepientcard = optionalCardRecepient.get();
        Double balance1 = recepientcard.getBalance();
        Double editedBalance = balance1 + transferDto.getAmount();
        recepientcard.setBalance(editedBalance);
        cardRepository.save(optionalCardRecepient.get());
        cardRepository.save(optionalCardSender.get());



        Income income = new Income();
        income.setFromCard(optionalCardSender.get());
        income.setToCard(optionalCardRecepient.get());
        income.setAmount(transferDto.getAmount());
        incomeRepository.save(income);


        Outcome outcome = new Outcome();
        outcome.setFromCard(optionalCardSender.get());
        outcome.setToCard(optionalCardRecepient.get());
        outcome.setCommision(commision);
        outcome.setAmount(transferDto.getAmount());
         outcomeRepository.save(outcome);
        return new ApiResponce("Transfer successfully processed", true);


    }

    public ApiResponce cardHistory(Integer cardId){

        Optional<Card> optionalCard = cardRepository.findById(cardId);

        if (!optionalCard.isPresent()){
            return new ApiResponce("Bunday card topilmadi",false);
        }

        Card card = optionalCard.get();

        User user = card.getUser();

        if (!user.getEmail().equals(username)){
            return new ApiResponce("Bu card Bu userga tegishli emas",false);
        }


        List<Outcome> allByFrom_card_idOutcome = outcomeRepository.findByFromCard_Id(cardId);
        List<Income> byAllFrom_card_id_idIncome = incomeRepository.findByToCard_Id(cardId);


        return new ApiResponce("Shu userga tegishli bolgan income va outcome lar ",true,allByFrom_card_idOutcome,byAllFrom_card_id_idIncome);

    }






}
