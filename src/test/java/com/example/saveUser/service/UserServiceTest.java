package com.example.saveUser.service;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import com.example.saveUser.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

//    @Test
//    void test_getUsers() {
//        List<UserModel> users = new ArrayList<UserModel>();
//        users.add(new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba"));
//        users.add(new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero"));
//
//        when(userService.getUsers()).thenReturn(users);
//        assertEquals(2,users.size());
//    }
//
//    @Test
//    void test_getUserById(){
//        List<UserModel> users = new ArrayList<>();
//        users.add(new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba"));
//        users.add(new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero"));
//
//        UserModel user = new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba");
//        String id = "1";
//
////        when(userRepository.findById(id)).thenReturn((users.stream().filter(user->user.getId()==id).collect(Collectors.toList()).size()));
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//        assertEquals(userRepository.findById(id).get().getUserName(),user.getUserName());
//
//    }
//
//    @Test
//    void test_createUser(){
//        UserModel newUser = new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero");
//
//        when(userRepository.save(newUser)).thenReturn(newUser);
//
//        assertEquals(userRepository.save(newUser).getUserName(),newUser.getUserName());
//    }
//
//    @Test
//    void test_updateUser(){
//        UserModel newUser = new UserModel("1","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero");
//        String userName = "Ranjan";
////        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(newUser));
//        UserModel updates = new UserModel(null,"Rosan",null,null,null,null,null);
//        String id = "1";
//        when(userRepository.findById(id)).thenReturn(Optional.of(newUser));
//        assertEquals(userRepository.findById(id).get(),newUser);
//
//        when(userService.updateUser(id,updates)).thenReturn("Successfully updated");
//        System.out.println(userService.updateUser(id,updates));
////        assertEquals(userService.updateUser(id,updates),"Successfully updated");
//    }

    UserModel user;
    @BeforeEach
    public void setUp(){
       user = new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba");
    }

    @AfterEach
    public void tearDown(){
        user = null;
    }

    @Test
    @DisplayName("Test for creation of user")
    public void test_createUser(){
        given(userRepository.save(user)).willReturn(user);
        ResponseEntity<?> savedUser = userService.createUser(user);
        assertThat(savedUser).isNotNull();
    }

    @Test
    @DisplayName("Test for get all users")
    public void test_getAllUsers(){
        UserModel user1 = new UserModel("1","Rosan","Rosan Patel", "rosanpatel@gmail.com","ktbl","8457044770","Alba");
        UserModel user2 = new UserModel("2","Ranjan","Ranjan Patel", "ranjanpatel@gmail.com","ktbl","8457044771","Nero");
        given(userRepository.findAll()).willReturn(List.of(user1,user2));
        ResponseEntity<List<UserModel>> users = userService.getUsers();
        assertThat(users).isNotNull();
        assertThat(users.getBody().size()).isEqualTo(2);
    }


    @Test
    @DisplayName("test to get all users (Negative test case)")
    public void test_getAllUsers_Neg(){
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        ResponseEntity<?> users = userService.getUsers();
//        assertThat(users).isEmpty();
//        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("test to get a single user using id")
    public void test_getUserById(){
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        UserModel foundUser = (UserModel) userService.getUserById(user.getId()).getBody();
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("test to get a single user using id(Negetive test case)")
    public void test_getUserById_Neg(){
        given(userRepository.findById("123")).willReturn(Optional.ofNullable(null));
        String foundUser = (String) userService.getUserById("123").getBody();
        assertThat(foundUser).isEqualTo("No such User exist with id: 123");
    }

    @Test
    @DisplayName("Test for updating userData")
    public void test_updateUser(){
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);

        user.setUserName("lol");

        UserModel updatedUser =(UserModel) userService.updateUser(user.getId(),user).getBody();
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUserName()).isEqualTo(user.getUserName());

    }

    @Test
    @DisplayName("Test for updating userData")
    public void test_replaceUserName(){

        given(userRepository.findByUserName(user.getUserName())).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);

        UserModel userWithUpdatedName = (UserModel) userService.replaceUserName(user.getUserName()).getBody();

//        System.out.println(userWithUpdatedName.getFullName());
        assertThat(userWithUpdatedName.getFullName().length()).isEqualTo(user.getFullName().length());
    }

    @Test
    @DisplayName("Test for deleting userDate")
    public void test_deleteByUserName(){
        given(userRepository.findByUserName(user.getUserName())).willReturn(Optional.of(user));
        willDoNothing().given(userRepository).deleteById(user.getId());

        userService.deleteByUserName(user.getUserName());
        verify(userRepository,times(1)).deleteById(user.getId());
    }

}