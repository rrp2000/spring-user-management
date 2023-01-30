package com.example.saveUser.controller;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public Object getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserModel userData){
        return userService.createUser(userData);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserModel updateData, @PathVariable String id){
        return userService.updateUser(id, updateData);
    }

    @PutMapping("replaceUser/{userName}")
    public ResponseEntity<?> replaceUserName(@PathVariable String userName){
        return userService.replaceUserName(userName);
    }

    @DeleteMapping("/deleteUser/{userName}")
    public ResponseEntity<?> deleteByUserName(@PathVariable String userName){
        return userService.deleteByUserName(userName);
    }


}
