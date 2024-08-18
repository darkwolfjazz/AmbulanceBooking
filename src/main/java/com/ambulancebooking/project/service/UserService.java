package com.ambulancebooking.project.service;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity SignupUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
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

    public UserEntity loginUser(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserEntity currentUser = user.get();
            if (passwordEncoder.matches(password, currentUser.getPassword())) {
                return currentUser; // Return the user if the login is successful
            }
        }
        return null; // Return null if login fails
    }
}
