package com.picpay_simplificado.services;


import com.picpay_simplificado.Dto.TransactionDto;
import com.picpay_simplificado.domain.transaction.Transaction;
import com.picpay_simplificado.domain.user.User;
import com.picpay_simplificado.repositories.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service

public class TransactionService {

    private RestTemplate restTemplate;
    private UserService userService;
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository,UserService userService,RestTemplate restTemplate)
    {
        this.transactionRepository=transactionRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    public void createTransaction(TransactionDto transaction) throws Exception {

        User sender = this.userService.findUserById(transaction.senderId());

        User receiver = this.userService.findUserById((transaction.receiverID()));

        userService.validateTransaction(sender,transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender,transaction.value());

        if(!isAuthorized)
        {
            throw  new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();

        newTransaction.setAmount(transaction.value());

        newTransaction.setSender(sender);

        newTransaction.setReceiver(receiver);

        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.transactionRepository.save(newTransaction);

        this.userService.saveUser(sender);

        this.userService.saveUser(receiver);

    }

    public boolean authorizeTransaction(User sender, BigDecimal value)
    {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    "https://util.devi.tools/api/v2/authorize", Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

                Object authValue = response.getBody().get("authorization");

                if (authValue instanceof Boolean) {
                    return (Boolean) authValue;
                }
            }
        } catch (Exception e) {

            System.err.println("Erro na autorização: " + e.getMessage());
        }

        return false;

    }



}
