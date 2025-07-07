package com.picpay_simplificado.controllers;


import com.picpay_simplificado.Dto.UserDto;
import com.picpay_simplificado.domain.user.User;
import com.picpay_simplificado.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto user)
    {
        User newUser = userService.createUSer(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping

    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> users = this.userService.findAllUsers();

        return new ResponseEntity<>(users,HttpStatus.OK);

    }




}
