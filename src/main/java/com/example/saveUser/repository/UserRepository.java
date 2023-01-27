package com.example.saveUser.repository;

import com.example.saveUser.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel,String> {
    @Query
    Optional<UserModel> findByUserName(String userName);

    @Query
    Optional<UserModel> findByEmail(String email);

    @Query
    Optional<UserModel> findByMobileNumber(String mobileNumber);

}
