package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.service.EmailService;
import com.ambulancebooking.project.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity userEntity) {
    UserEntity user= userService.SignupUser(userEntity);
    emailService.sendWelcomeMessage(user.getEmail());
    return new ResponseEntity<>(user,HttpStatus.CREATED);
}

@PutMapping("/reset")
    public String updateCredentials(@PathVariable Long userId,@Valid @RequestBody UserEntity user){
     userService.updateUserCredentials(userId,user);
     return "Credentials have been reset";
}

@PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password){
    String result= userService.loginUser(email, password);
    if("Success".equals(result)){
        return ResponseEntity.ok("Login successful!, Welcome");
    }else{
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
    }
}
}
