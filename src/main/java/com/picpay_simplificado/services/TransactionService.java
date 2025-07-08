package com.picpay_simplificado.services;


import com.picpay_simplificado.Dto.TransactionDto;
import com.picpay_simplificado.domain.transaction.Transaction;
import com.picpay_simplificado.domain.user.User;
import com.picpay_simplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class TransactionService {

    @Value("${external.api.authorization.url}")
    private String authorizationUrl;
    private final RestTemplate restTemplate;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository,UserService userService,RestTemplate restTemplate,NotificationService notificationService)
    {
        this.transactionRepository=transactionRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
    }
    public Transaction createTransaction(TransactionDto transaction) throws Exception {

        User sender = this.userService.findUserById(transaction.senderId());

        User receiver = this.userService.findUserById((transaction.receiverId()));

        userService.validateTransaction(sender,transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender,transaction.value());

        if(!isAuthorized)
        {
            throw new Exception("Transação não autorizada");
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

        this.notificationService.sendNotification(sender,"Transação realizada com sucesso!");

        this.notificationService.sendNotification(receiver,"Transação recebida com sucesso!");

        return newTransaction;
    }

    public List<Transaction> findAllTransactions()
    {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findTransactionById(Long id)
    {
       return transactionRepository.findById(id);
    }


    public boolean authorizeTransaction(User sender, BigDecimal value)
    {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    authorizationUrl, Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object data = response.getBody().get("status");

                return "success".equalsIgnoreCase(data.toString());
            }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return false;
    }



}
