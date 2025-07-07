package com.picpay_simplificado.services;

import com.picpay_simplificado.domain.user.User;
import com.picpay_simplificado.domain.user.UserType;
import com.picpay_simplificado.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT)
        {
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação!");
        }
        if(sender.getBalance().compareTo(amount)<0)
        {
            throw new Exception("Saldo insuficiente!");
        }

    }

    public User findUserById(Long id) throws Exception {

        return this.userRepository.findUserById(id).orElseThrow(()-> new Exception("Usuário não encontrado!"));
    }

    public void saveUser(User user)
    {
        this.userRepository.save(user);
    }

}
