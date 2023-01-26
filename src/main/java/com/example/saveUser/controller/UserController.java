package com.example.saveUser.controller;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.service.UserService;
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
    public List<UserModel> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<UserModel> getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PostMapping("/user")
    public UserModel createUser(@RequestBody UserModel userData){
        return userService.createUser(userData);
    }

    @PutMapping("/user/{id}")
    public String updateUser(@RequestBody UserModel updateData, @PathVariable String id){
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
