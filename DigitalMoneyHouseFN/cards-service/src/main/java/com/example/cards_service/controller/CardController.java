package com.example.cards_service.controller;


import com.example.cards_service.entities.Card;
import com.example.cards_service.exceptions.BadRequestException;
import com.example.cards_service.exceptions.ConflictException;
import com.example.cards_service.exceptions.ResourceNotFoundException;
import com.example.cards_service.service.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping("/{id}/all-cards")
    public ResponseEntity<?> getAllCardsByAccountId(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getAllCardsByAccountId(id));
    }

    @GetMapping("/{accountId}/card/{cardId}")
    public ResponseEntity<?> getCardByIdAndAccountId (@PathVariable Long accountId, @PathVariable Long cardId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardByIdAndAccountId(cardId, accountId));
    }

    @GetMapping("/{accountId}/cardNumber/{cardNumber}")
    public ResponseEntity<?> getCardByNumberAndAccountId (@PathVariable Long accountId, @PathVariable String cardNumber) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.getCardByNumberAndAccountId(cardNumber, accountId));
    }

    @PostMapping("/register-card")
    public ResponseEntity<?> registerCard(@RequestBody Card card) throws BadRequestException, ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.registerCard(card));
    }

    @DeleteMapping("/{accountId}/card/{cardNumber}")
    @Transactional
    public ResponseEntity<?> deleteCard(@PathVariable Long accountId, @PathVariable String cardNumber) throws ResourceNotFoundException {
        cardService.deleteCard(cardNumber, accountId);
        return ResponseEntity.ok().build();
    }
}
