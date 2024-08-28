package com.ambulancebooking.project.service;

import com.ambulancebooking.project.entity.UserEntity;
import com.ambulancebooking.project.jwt.JwtHelper;
import com.ambulancebooking.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity SignupUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    //Newly added feature still to work on-------------------------------
    public void updateUserCredentials(Long userId, UserEntity user) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Hash the password before saving
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        // Update other fields if needed
        userRepository.save(existingUser);
    }

    public String loginUser(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserEntity currentUser = user.get();
            if (passwordEncoder.matches(password, currentUser.getPassword())) {
                // Generate and return the JWT token
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                return jwtHelper.generateToken(userDetails);
            }
        }
        return null; // Return null if login fails
    }
}
