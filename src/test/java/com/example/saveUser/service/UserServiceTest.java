package com.example.saveUser.service;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void test_getUsers() {
        List<UserModel> users = new ArrayList<UserModel>();
        users.add(new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba"));
        users.add(new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero"));

        when(userService.getUsers()).thenReturn(users);
        assertEquals(2,users.size());
    }

    @Test
    void test_getUserById(){
        List<UserModel> users = new ArrayList<>();
        users.add(new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba"));
        users.add(new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero"));

        UserModel user = new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba");
        String id = "1";

//        when(userRepository.findById(id)).thenReturn((users.stream().filter(user->user.getId()==id).collect(Collectors.toList()).size()));
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        assertEquals(userRepository.findById(id).get().getUserName(),user.getUserName());

    }

    @Test
    void test_createUser(){
        UserModel newUser = new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero");

        when(userRepository.save(newUser)).thenReturn(newUser);

        assertEquals(userRepository.save(newUser).getUserName(),newUser.getUserName());
    }

    @Test
    void test_updateUser(){
        UserModel newUser = new UserModel("1","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero");
        String userName = "Ranjan";
//        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(newUser));
        UserModel updates = new UserModel(null,"Rosan",null,null,null,null,null);
        String id = "1";
        when(userRepository.findById(id)).thenReturn(Optional.of(newUser));
        assertEquals(userRepository.findById(id).get(),newUser);

//        when(userService.updateUser(id,updates)).thenReturn("Successfully updated");
//        assertEquals(userService.updateUser(id,updates),"Successfully updated");
    }

}