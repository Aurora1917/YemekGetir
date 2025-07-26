package com.yemekgetir.userservice.service;


import com.yemekgetir.userservice.entity.User;
import com.yemekgetir.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user)
    {
        return userRepository.save(user);
    }


    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void deleteUserById(Long userId)
    {
        userRepository.deleteById(userId);
    }

}

