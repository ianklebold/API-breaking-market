package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.service.MarketUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingmarket/v1/user")
public class UserController {

    @Autowired
    MarketUserService marketUserService;
    
    @PostMapping("/newuser")
    public User postNewUser(@Valid @RequestBody User user) {
        /**
         * Para crear un nuevo usuario
         */
        return marketUserService.newUser(user);

    }

    @GetMapping("/alluser")
    public ArrayList<User> getAllUser() {
        /**
         * Obtener todos los usuarios
         */
        return marketUserService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable(name="id") Long id ){
        /**
         * Eliminar un usuario
         * 
         *  --> Eliminar un usuario implica eliminar todos sus carritos.
         */
        Optional<User> user = marketUserService.getUserById(id);
        marketUserService.deleteUser(user.get());
    }

    @PutMapping(value="/update/{id}")
    public User updateUser(@PathVariable(name = "id") Long id, @RequestBody User user) {
        /**
         * Actualizar los datos de un usuario
         * 
         * --> La actualizacion de un usuario implica actualizar los datos a sus
         *     Entidades que dependan de él.
         */
        
        Optional<User> foundUser = marketUserService.getUserById(id);
        user.setId(id);
        return marketUserService.updateUser(user, foundUser);

    }
    
}
