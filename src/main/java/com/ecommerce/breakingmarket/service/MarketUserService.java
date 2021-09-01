package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketUserService {
    
    @Autowired
    UserRepository userRepository;

    public User newUser(User user){
        return userRepository.save(user);
    }

    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>) userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public User updateUser(User user, Optional<User> foundUser){
        
        user.setId(foundUser.get().getId());
        user.setRegistration(foundUser.get().getRegistration());

        return userRepository.save(user);
    }

}
