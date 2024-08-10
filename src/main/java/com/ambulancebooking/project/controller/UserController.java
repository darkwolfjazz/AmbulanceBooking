package com.ambulancebooking.project.controller;

import com.ambulancebooking.project.entity.AmbulanceEntity;
import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

@Autowired
private UserService userService;
@PostMapping("/signup")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
    UserEntity user= userService.SignupUser(userEntity);
    return new ResponseEntity<>(user,HttpStatus.CREATED);
}
}
