package com.picpay_simplificado.repositories;

import com.picpay_simplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserById(Long id);

    Optional<User> findUserByDocument(String document);

}
