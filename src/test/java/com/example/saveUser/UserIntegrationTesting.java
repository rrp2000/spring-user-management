package com.example.saveUser;

import com.example.saveUser.controller.UserController;
import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import com.example.saveUser.service.UserService;
import org.json.JSONException;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserIntegrationTesting {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private WebClient.Builder webTestClient;
//
//
//    TestRestTemplate restTemplate = new TestRestTemplate();
//
//    HttpHeaders headers = new HttpHeaders();
//
//    @Test
//    public void testUser() throws JSONException {
//        UserModel newUser = new UserModel(
//                null,
//                "Rosan",
//                "Rosan Patel",
//                "rosanPatel2000@gmail.com",
//                "ktbl",
//                "8457044777",
//                "alba"
//        );
//
//        //Creating a user data
//        ResponseEntity<UserModel>  userCreationResponse = webTestClient.baseUrl("http://localhost:" + port)
//                .build()
//                .post()
//                .uri("/user")
//                .bodyValue(newUser)
//                .retrieve().toEntity(UserModel.class).block();
//
//        assertEquals(userCreationResponse.getStatusCode(), HttpStatus.CREATED);
//        assertEquals(userCreationResponse.getBody().getUserName(), newUser.getUserName());
//
//        //getting all the users
//        ResponseEntity<List<UserModel>> users = webTestClient.baseUrl("http://localhost:" + port)
//                .build()
//                .get()
//                .uri("/users")
//                .retrieve().toEntityList(UserModel.class).block();;
//
//        assertEquals(users.getStatusCode(), HttpStatus.OK);
//        assertEquals(users.getBody().size(),1);
//
//
//        //get user details with id
//        ResponseEntity<UserModel> userWIthId = webTestClient.baseUrl("http://localhost:" + port)
//                .build()
//                .get()
//                .uri("users/" + userCreationResponse.getBody().getId())
//                .retrieve().toEntity(UserModel.class).block();
//
//        assertEquals(userWIthId.getStatusCode(), HttpStatus.OK);
//        assertEquals(userWIthId.getBody().getUserName(), userCreationResponse.getBody().getUserName());
//
//        //updating user details
//        ResponseEntity<UserModel> updatedUser = webTestClient.baseUrl("http://localhost:" + port)
//                .build()
//                .put()
//                .uri("user/" + userCreationResponse.getBody().getId())
//                .bodyValue(new UserModel(null,"Ranjan",null,null,null,null,null))
//                .retrieve().toEntity(UserModel.class).block();
//        assertEquals(updatedUser.getStatusCode(), HttpStatus.OK);
//        assertEquals(updatedUser.getBody().getUserName(), "Ranjan");
//
//        //update the full name of the user
//        ResponseEntity<UserModel> updatedNameUser = webTestClient.baseUrl("http://localhost:" + port)
//                .build()
//                .put()
//                .uri("replaceUser/" + updatedUser.getBody().getUserName())
//                .retrieve().toEntity(UserModel.class).block();
//        assertEquals(updatedNameUser.getStatusCode(), HttpStatus.OK);
//        assertEquals(updatedNameUser.getBody().getFullName().length(), newUser.getFullName().length());
//
//        //delete a user
//        ResponseEntity<String> deleteUser = webTestClient.baseUrl("http://localhost:" + port)
//                .build()
//                .delete()
//                .uri("deleteUser/" + updatedUser.getBody().getUserName())
//                .retrieve().toEntity(String.class).block();
//        assertEquals(deleteUser.getStatusCode(), HttpStatus.OK);
//        assertEquals(deleteUser.getBody(), "Deleted successfully");
//
//    }
//
//}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTesting {
    @LocalServerPort
    private int port;

    @Autowired
    private WebClient.Builder webClient;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    @InjectMocks
    UserService userService;


    @BeforeEach
    public void setUp() {
        Mockito.reset(userRepository);
    }
    @Test
    public void testUser() throws JSONException {

        UserModel newUser = new UserModel(
                null,
                "randomUser101",
                "Rosan Patel",
                "rosanPateldamo101@gmail.com",
                "ktbl",
                "8457044101",
                "alba"
        );

        given(userRepository.save(newUser)).willReturn(newUser);
        given(userRepository.findAll()).willReturn(List.of(newUser));
        given(userRepository.findById(newUser.getId())).willReturn(Optional.of(newUser));
        given(userRepository.findByUserName(newUser.getUserName())).willReturn(Optional.of(newUser));


        //Creating a user data
        ResponseEntity<UserModel>  userCreationResponse = webClient.baseUrl("http://localhost:" + port)
                .build()
                .post()
                .uri("/user")
                .bodyValue(newUser)
                .retrieve().toEntity(UserModel.class).block();

        assertEquals(userCreationResponse.getStatusCode(), HttpStatus.CREATED);
        assertEquals(userCreationResponse.getBody().getUserName(), newUser.getUserName());

//        getting all the users
        ResponseEntity<List<UserModel>> users = webClient.baseUrl("http://localhost:" + port)
              .build()
              .get()
              .uri("/users")
              .retrieve().toEntityList(UserModel.class).block();;
        System.out.println(users.getBody());

        assertEquals(users.getStatusCode(), HttpStatus.OK);
//        assertEquals(users.getBody().size(),1);
//
//        //get user details with id
        ResponseEntity<UserModel> userWIthId = webClient.baseUrl("http://localhost:" + port)
               .build()
               .get()
               .uri("users/" + userCreationResponse.getBody().getId())
               .retrieve().toEntity(UserModel.class).block();
        assertEquals(userWIthId.getStatusCode(), HttpStatus.OK);
        assertEquals(userWIthId.getBody().getUserName(), userCreationResponse.getBody().getUserName());
////
////        //updating user details
        ResponseEntity<UserModel> updatedUser = webClient.baseUrl("http://localhost:" + port)
              .build()
              .put()
              .uri("user/" + userCreationResponse.getBody().getId())
                .bodyValue(new UserModel(null,null,null,null,"hyderabad",null,null))
                .retrieve().toEntity(UserModel.class).block();
//
        assertEquals(updatedUser.getStatusCode(), HttpStatus.OK);
        assertEquals(updatedUser.getBody().getAddress(), "hyderabad");
////
//        //update the full name of the user
        ResponseEntity<UserModel> updatedNameUser = webClient.baseUrl("http://localhost:" + port)
              .build()
              .put()
              .uri("replaceUser/" + updatedUser.getBody().getUserName())
                .retrieve().toEntity(UserModel.class).block();
        assertEquals(updatedNameUser.getStatusCode(), HttpStatus.OK);
        assertEquals(updatedNameUser.getBody().getFullName().length(), newUser.getFullName().length());
//
//        //deleting the user
        ResponseEntity<String> deleteUser = webClient.baseUrl("http://localhost:" + port)
               .build()
               .delete()
               .uri("deleteUser/" + userCreationResponse.getBody().getUserName())
               .retrieve().toEntity(String.class).block();
        assertEquals(deleteUser.getStatusCode(), HttpStatus.OK);
        assertEquals(deleteUser.getBody(), "Deleted successfully");



    }
    @After
    public void tearDown() {
        reset();
    }


}
