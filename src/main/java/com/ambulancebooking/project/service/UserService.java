package com.ambulancebooking.project.service;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity SignupUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }



}
