package com.picpay_simplificado.controllers;


import com.picpay_simplificado.Dto.TransactionDto;
import com.picpay_simplificado.domain.transaction.Transaction;
import com.picpay_simplificado.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")

public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transaction) throws Exception {

        Transaction newTransaction = this.transactionService.createTransaction(transaction);

        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions()
    {
        List<Transaction> transactions = transactionService.findAllTransactions();

        return new ResponseEntity<>(transactions,HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Optional<Transaction>> getById(@RequestParam Long id)
    {
        Optional<Transaction> transaction = transactionService.findTransactionById(id);

        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }





}
