package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketUserService {
    
    @Autowired
    UserRepository userRepository;

    public User newUser(User user){

        System.out.println("Estoy en el service");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());

        if (user.getPassword().length()>=8 && validateEmail(user.getEmail())) {
            System.out.println("Todo bien");

                return userRepository.save(user);
        }else{
            System.out.println("Todo mal");

            return null;
        }

    }

    public ArrayList<User> getAllUsers(){

        for (User usuario : (ArrayList<User>) userRepository.findAll() ) {
            usuario.setPassword(" ");
            usuario.setCart(null);
        }
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

    public static Boolean validateEmail(String email)
    {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        System.out.println(matcher.matches());
        return matcher.matches();
    }

}
