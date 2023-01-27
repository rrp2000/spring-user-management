package com.example.saveUser.repository;

import com.example.saveUser.model.UserModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    static String userName = "Rosan";
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void  setUp(){
        System.out.println("before");
        userRepository.deleteAll();
    }
    @Test
    void findByUserName() {

        UserModel newUser = new UserModel(null,userName,"Rosan Ranjan Patel", "ranjan@gamil.com","haripur","8457044770","Albanero" );
        userRepository.save(newUser);
        Optional<UserModel> getUser = userRepository.findByUserName(userName);
        assertThat(getUser.isPresent()).isTrue();
        System.out.println("First");
        assertThat(getUser.get().getUserName()).isEqualTo(newUser.getUserName());
        System.out.println("Second");
        assertThat(getUser.get().getFull_name()).isEqualTo(newUser.getFull_name());
        System.out.println("Third");
        userRepository.delete(newUser);
    }

    @AfterEach
    void tearDown(){
        System.out.println("after");
    }
}
