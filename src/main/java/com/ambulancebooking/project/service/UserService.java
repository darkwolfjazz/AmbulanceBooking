package com.ambulancebooking.project.service;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public UserEntity SignupUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    //Newly added feature still to work on-------------------------------
    public UserEntity updateUserCredentials(Long id,UserEntity updatedUser){
        Optional<UserEntity> existinguser = userRepository.findById(id);
        if(existinguser.isPresent()) {
            UserEntity currentUser = existinguser.get();
            currentUser.setUserName(updatedUser.getUserName());
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setPassword(updatedUser.getPassword());
            currentUser.setContactNumber(updatedUser.getContactNumber());
            userRepository.save(currentUser);
        }else {
            throw new RuntimeException("User not found!");
        }
        return updatedUser;
    }

    public String loginUser(String email,String password){
        Optional<UserEntity> user=userRepository.findByEmail(email);
        if(user.isPresent()){
            UserEntity currentUser=user.get();
//            System.out.println("User found with email: " + currentUser.getEmail());
//            System.out.println("Stored password: " + currentUser.getPassword());
//            System.out.println("Provided password: " + password);
            if(currentUser.getPassword().equals(password)){
                //System.out.println("Password match successful");
                return "Success";
            }else{
                //System.out.println("Password match failed");
                return "Failure";
            }
        }
        return "No user is present";
    }




}
