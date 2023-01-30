package com.example.saveUser;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.service.UserService;
import org.apache.catalina.User;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTesting {

    @LocalServerPort
    private int port;

    @Autowired
    private WebClient.Builder webTestClient;

//    @MockBean
//    private UserService userService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testUser() throws JSONException {
        UserModel newUser = new UserModel(
                null,
                "Rosan",
                "Rosan Patel",
                "rosanPatel2000@gmail.com",
                "ktbl",
                "8457044777",
                "alba"
        );

        //Creating a user data
        ResponseEntity<UserModel>  userCreationResponse = webTestClient.baseUrl("http://localhost:" + port)
                .build()
                .post()
                .uri("/user")
                .bodyValue(newUser)
                .retrieve().toEntity(UserModel.class).block();

        assertEquals(userCreationResponse.getStatusCode(), HttpStatus.CREATED);
        assertEquals(userCreationResponse.getBody().getUserName(), newUser.getUserName());

        //getting all the users
        ResponseEntity<List<UserModel>> users = webTestClient.baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/users")
                .retrieve().toEntityList(UserModel.class).block();;

        assertEquals(users.getStatusCode(), HttpStatus.OK);
        assertEquals(users.getBody().size(),1);


        //get user details with id
        ResponseEntity<UserModel> userWIthId = webTestClient.baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("users/" + userCreationResponse.getBody().getId())
                .retrieve().toEntity(UserModel.class).block();

        assertEquals(userWIthId.getStatusCode(), HttpStatus.OK);
        assertEquals(userWIthId.getBody().getUserName(), userCreationResponse.getBody().getUserName());

        //updating user details
        ResponseEntity<UserModel> updatedUser = webTestClient.baseUrl("http://localhost:" + port)
                .build()
                .put()
                .uri("user/" + userCreationResponse.getBody().getId())
                .bodyValue(new UserModel(null,"Ranjan",null,null,null,null,null))
                .retrieve().toEntity(UserModel.class).block();
        assertEquals(updatedUser.getStatusCode(), HttpStatus.OK);
        assertEquals(updatedUser.getBody().getUserName(), "Ranjan");

        //update the full name of the user
        ResponseEntity<UserModel> updatedNameUser = webTestClient.baseUrl("http://localhost:" + port)
                .build()
                .put()
                .uri("replaceUser/" + updatedUser.getBody().getUserName())
                .retrieve().toEntity(UserModel.class).block();
        assertEquals(updatedNameUser.getStatusCode(), HttpStatus.OK);
        assertEquals(updatedNameUser.getBody().getFullName().length(), newUser.getFullName().length());

        //delete a user
        ResponseEntity<String> deleteUser = webTestClient.baseUrl("http://localhost:" + port)
                .build()
                .delete()
                .uri("deleteUser/" + updatedUser.getBody().getUserName())
                .retrieve().toEntity(String.class).block();
        assertEquals(deleteUser.getStatusCode(), HttpStatus.OK);
        assertEquals(deleteUser.getBody(), "Deleted successfully");

    }

}
