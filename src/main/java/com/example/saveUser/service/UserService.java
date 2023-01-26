package com.example.saveUser.service;

import com.example.saveUser.model.UserModel;
import com.example.saveUser.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public Optional<UserModel> getUserById(String id){
        return userRepository.findById(id);
    }

   public String updateUser(String id, UserModel updateData){
        Optional<UserModel> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            UserModel oldUser = userOptional.get();
            oldUser.setUserName(updateData.getUserName()!=null?updateData.getUserName(): oldUser.getUserName());
            oldUser.setFull_name(updateData.getFull_name()!=null?updateData.getFull_name(): oldUser.getFull_name());
            oldUser.setEmail(updateData.getEmail()!=null?updateData.getEmail():oldUser.getEmail());
            oldUser.setAddress(updateData.getAddress()!=null?updateData.getAddress():oldUser.getAddress());
            oldUser.setMobile_number(updateData.getMobile_number()!=null?updateData.getMobile_number(): oldUser.getMobile_number());
            oldUser.setCurrent_organizations(updateData.getCurrent_organizations()!=null?updateData.getCurrent_organizations():oldUser.getCurrent_organizations());
            userRepository.save(oldUser);
            return "Updated successfully";
        }else{
            return "No such user with this id";
        }
   }

   public ResponseEntity<?> replaceUserName(String userName){
        Optional<UserModel> userOptional = userRepository.findByUserName(userName);
        if(userOptional.isPresent()) {
            UserModel user = userOptional.get();
            String fullName = user.getFull_name();
            char specialChars[] = {'@','#','$','%','&','*'};
            for(int i = 0;i<fullName.length();i++){
                if(fullName.charAt(i)=='a' ||fullName.charAt(i)=='e' ||fullName.charAt(i)=='i' ||fullName.charAt(i)=='o' ||fullName.charAt(i)=='u'){
                    Random rand = new Random();
                    int index = Math.abs(rand.nextInt()%specialChars.length);
                    fullName = fullName.substring(0,i)+specialChars[index]+fullName.substring(i+1);
                }
            }
            user.setFull_name(fullName);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No such data available with "+userName,HttpStatus.NOT_FOUND);
        }
   }

}
