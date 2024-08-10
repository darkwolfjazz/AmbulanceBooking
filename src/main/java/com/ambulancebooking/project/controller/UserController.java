package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.service.EmailService;
import com.ambulancebooking.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

@Autowired
private UserService userService;
@Autowired
    private  EmailService emailService;
@PostMapping("/signup")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
    UserEntity user= userService.SignupUser(userEntity);
    emailService.sendWelcomeMessage(user.getEmail());
    return new ResponseEntity<>(user,HttpStatus.CREATED);
}

@PutMapping("/reset")
    public String updateCredentials(@PathVariable Long userId,@RequestBody UserEntity user){
     userService.updateUserCredentials(userId,user);
     return "Credentials have been reset";
}



}
