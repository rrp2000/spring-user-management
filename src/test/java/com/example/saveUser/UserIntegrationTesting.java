package com.example.saveUser;

import com.example.saveUser.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTesting {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testUser() {
        UserModel newUser = new UserModel(null,"Rosan","Rosan Patel","rosanPatel2000@gmail.com", "ktbl", "8457044777","alba");
        var userCreationResponse = this.webTestClient
                .post()
                .uri("/user")
                .body(Mono.just(newUser),UserModel.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(UserModel.class);

//        this.webTestClient
//                .get()
//                .uri("/users/{id}",userCreationResponse.getResponseBody().getId())
//                .exchange()
//                .expectStatus()
//                .isEqualTo(HttpStatus.OK)
//                .expectBody(UserModel.class)
//                .isEqualTo(newUser);
        this.webTestClient
                .get()
                .uri("/users")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK);
//                .expectBody(List.class)
//                .isEqualTo(List.of(newUser));

        this.webTestClient
               .delete()
               .uri("/deleteUser/{userName}",newUser.getUserName())
                .exchange()
                .expectStatus().isOk();
    }

}
