package com.example.saveUser.service;

import com.example.saveUser.exception.UserException;
import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserException userException;


    public ResponseEntity<List<UserModel>> getUsers(){
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<?> createUser(UserModel user){
        Optional<UserModel> userWithEmail = userRepository.findByEmail(user.getEmail());
        if(userWithEmail.isPresent()){
            return new ResponseEntity<>("Email already exists. Please give another one.",HttpStatus.BAD_REQUEST);
        }
        Optional<UserModel> userWithMobile = userRepository.findByMobileNumber(user.getMobileNumber());
        if(userWithMobile.isPresent()){
            return new ResponseEntity<>("Mobile number already exists. Please give another one.",HttpStatus.BAD_REQUEST);
        }
        UserModel res = userRepository.save(user);
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    public ResponseEntity<?> getUserById(String id){
        Optional<UserModel> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>("No such User exist with id: "+id,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(userOptional.get(),HttpStatus.OK);
        }
    }


   public ResponseEntity<?> updateUser(String id, UserModel updateData){
        Optional<UserModel> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            UserModel oldUser = userOptional.get();
            oldUser.setUserName(updateData.getUserName()!=null?updateData.getUserName(): oldUser.getUserName());
            oldUser.setFullName(updateData.getFullName()!=null?updateData.getFullName(): oldUser.getFullName());
            oldUser.setEmail(updateData.getEmail()!=null?updateData.getEmail():oldUser.getEmail());
            oldUser.setAddress(updateData.getAddress()!=null?updateData.getAddress():oldUser.getAddress());
            oldUser.setMobileNumber(updateData.getMobileNumber()!=null?updateData.getMobileNumber(): oldUser.getMobileNumber());
            oldUser.setCurrentOrganizations(updateData.getCurrentOrganizations()!=null?updateData.getCurrentOrganizations():oldUser.getCurrentOrganizations());
            userRepository.save(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No such user with this id",HttpStatus.NOT_FOUND);
        }
   }

   public ResponseEntity<?> replaceUserName(String userName){
        Optional<UserModel> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent()) {
            UserModel user = userOptional.get();
            String fullName = user.getFullName();
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
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No such data available with "+userName,HttpStatus.NOT_FOUND);
        }
   }


   public ResponseEntity<?> deleteByUserName(String userName){
        Optional<UserModel> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent()){
            UserModel user = userOptional.get();
            try{
                userRepository.deleteById(user.getId());
                return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("No such data available with "+userName,HttpStatus.NOT_FOUND);
        }
   }

}
