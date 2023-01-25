package com.example.saveUser.service;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getUsers(){
        return userRepository.findAll();
    }

    public UserModel createUser(UserModel user){
        UserModel res = userRepository.save(user);
        return res;
    }

    public Optional<UserModel> getUserById(int id){
        return userRepository.findById(id);
    }

}
