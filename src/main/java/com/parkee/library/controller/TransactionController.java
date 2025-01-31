package com.parkee.library.controller;

import com.parkee.library.model.Transaction;
import com.parkee.library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found! id: " + id);
        }
    }

    @PostMapping("/{isbnNumber}/{memberIdCardNumber}/{returnDate}")
    public ResponseEntity<?> borrowBook(@PathVariable Long isbnNumber,
                               @PathVariable Long memberIdCardNumber,
                               @PathVariable String returnDate) {
        try {
            return ResponseEntity.ok(transactionService.borrowBook(isbnNumber, memberIdCardNumber, returnDate));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/return/{id}")
    public Transaction returnBook(@PathVariable Long id) {
        return transactionService.returnBook(id);
    }

}
