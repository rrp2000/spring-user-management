package com.example.saveUser.controller;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserModel> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<UserModel> getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public UserModel createUser(@RequestBody UserModel userData){
        return userService.createUser(userData);
    }
}
