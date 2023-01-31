package com.example.saveUser.service;

import com.example.saveUser.exception.UserException;
import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserException userException;


    public ResponseEntity<List<UserModel>> getUsers(){
        logger.info("Found all users");
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> createUser(UserModel user){
        logger.info("Checking for existing email");
        Optional<UserModel> userWithEmail = userRepository.findByEmail(user.getEmail());
        if(userWithEmail.isPresent()){
            logger.error("Email already exists");
            return new ResponseEntity<>("Email already exists. Please give another one.",HttpStatus.BAD_REQUEST);
        }
        logger.info("checking for existing mobile number");
        Optional<UserModel> userWithMobile = userRepository.findByMobileNumber(user.getMobileNumber());
        if(userWithMobile.isPresent()){
            logger.error("Mobile number already exists");
            return new ResponseEntity<>("Mobile number already exists. Please give another one.",HttpStatus.BAD_REQUEST);
        }
        UserModel res = userRepository.save(user);
        logger.info("created new user successfully");
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    public ResponseEntity<?> getUserById(String id){
        logger.info("Finding user by id "+id);
        Optional<UserModel> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            logger.error("User not found with given id: "+id);
            return new ResponseEntity<>("No such User exist with id: "+id,HttpStatus.NOT_FOUND);
        }else{
            logger.info("User found with given id: "+id);
            return new ResponseEntity<>(userOptional.get(),HttpStatus.OK);
        }
    }


   public ResponseEntity<?> updateUser(String id, UserModel updateData){
        logger.info("Finding user by id "+id+ "to update user details");
        Optional<UserModel> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            UserModel oldUser = userOptional.get();
            oldUser.setUserName(updateData.getUserName()!=null?updateData.getUserName(): oldUser.getUserName());
            oldUser.setFullName(updateData.getFullName()!=null?updateData.getFullName(): oldUser.getFullName());
            oldUser.setEmail(updateData.getEmail()!=null?updateData.getEmail():oldUser.getEmail());
            oldUser.setAddress(updateData.getAddress()!=null?updateData.getAddress():oldUser.getAddress());
            oldUser.setMobileNumber(updateData.getMobileNumber()!=null?updateData.getMobileNumber(): oldUser.getMobileNumber());
            oldUser.setCurrentOrganizations(updateData.getCurrentOrganizations()!=null?updateData.getCurrentOrganizations():oldUser.getCurrentOrganizations());
            logger.info("updating user data");
            userRepository.save(oldUser);
            logger.info("updated user successfully");
            return new ResponseEntity<>(oldUser,HttpStatus.OK);

        }else{
            logger.error("User not found with given id: "+id);
            return new ResponseEntity<>("No such User exist with id: "+id,HttpStatus.NOT_FOUND);
        }

   }

   public ResponseEntity<?> replaceUserName(String userName){
        logger.info("Finding user by username "+userName);
        Optional<UserModel> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent()) {
            logger.info("User found with given username: "+userName);
            UserModel user = userOptional.get();
            String fullName = user.getFullName();
            logger.info("Replacing full Name vowels with random special characters");
            char specialChars[] = {'@','#','$','%','&','*'};
            for(int i = 0;i<fullName.length();i++){
                if(fullName.charAt(i)=='a' ||fullName.charAt(i)=='e' ||fullName.charAt(i)=='i' ||fullName.charAt(i)=='o' ||fullName.charAt(i)=='u'){
                    Random rand = new Random();
                    int index = Math.abs(rand.nextInt()%specialChars.length);
                    fullName = fullName.substring(0,i)+specialChars[index]+fullName.substring(i+1);
                }
            }
            user.setFullName(fullName);
            userRepository.save(user);
            logger.info("updated the full name successfully");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            logger.error("User not found with given username: "+userName+" while replacing full name");
            return new ResponseEntity<>("No such User exist with username: "+userName,HttpStatus.NOT_FOUND);
        }
   }


   public ResponseEntity<?> deleteByUserName(String userName){
        logger.info("Finding user by username "+userName+" to delete user");
        Optional<UserModel> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent()){
            logger.info("User found with given username: "+userName);
            UserModel user = userOptional.get();
            try{
                logger.info("trying to delete user");
                userRepository.deleteById(user.getId());
                logger.info("deleted user successfully");
                return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
            }catch(Exception e){
                logger.error(e.getMessage());
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            logger.error("User not found with given username: "+userName);
            return new ResponseEntity<>("No such data available with "+userName,HttpStatus.NOT_FOUND);
        }
   }

}
