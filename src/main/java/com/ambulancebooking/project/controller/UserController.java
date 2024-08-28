package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.jwt.JwtHelper;
import com.ambulancebooking.project.service.EmailService;
import com.ambulancebooking.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

@Autowired
private UserService userService;
@Autowired
    private  EmailService emailService;

@Autowired
private JwtHelper helper;

@Autowired
private UserDetailsService userDetailsService;

@Autowired
    private AuthenticationManager authenticationManager;



@PostMapping("/signup")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity userEntity) {
    System.out.println("Recieved signup request for " +userEntity);
    UserEntity user= userService.SignupUser(userEntity);
    emailService.sendWelcomeMessage(user.getEmail(), user.getUserName());
    return new ResponseEntity<>(user,HttpStatus.CREATED);
}

@PutMapping("/reset/{userId}")
    public String updateCredentials(@PathVariable Long userId,@Valid @RequestBody UserEntity user){
     userService.updateUserCredentials(userId,user);
     return "Credentials have been reset";
}
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            // Authenticate the user using the AuthenticationManager
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authentication);

            // Generate the JWT token
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = helper.generateToken(userDetails);

            return ResponseEntity.ok("Login Successful! Your token is " + token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
