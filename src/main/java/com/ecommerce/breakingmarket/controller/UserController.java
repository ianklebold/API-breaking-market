package com.ecommerce.breakingmarket.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.service.MarketUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingmarket/v1/user")
public class UserController {

    @Autowired
    MarketUserService marketUserService;
    
    @PostMapping("/newuser")
    public ResponseEntity<?> postNewUser(@Valid @RequestBody User user) {
        /**
         * Para crear un nuevo usuario
         */

        if(marketUserService.newUser(user) == null){

            return ResponseEntity.status(HttpStatus.CONFLICT)
                .header("Estado de usuario", "Usuario : "+ user.getId() +" Ha ocurrido un conflicto")
                .body("Error en el procesamiento de datos");
            
        }else{
            new ResponseEntity<>(user, HttpStatus.CONFLICT);
            return ResponseEntity.ok()
                .header("Estado de usuario", "Usuario : "+ user.getId() +" Creado exitosamente")
                .body(user);
        }

    }

    

    @GetMapping("/alluser")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> getAllUser() {
        /**
         * Obtener todos los usuarios
         */
        return marketUserService.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name="id") Long id ){
        /**
         * Eliminar un usuario
         * 
         *  --> Eliminar un usuario implica eliminar todos sus carritos.
         */
        Optional<User> user = marketUserService.getUserById(id);
        if (user.isPresent()){
            marketUserService.deleteUser(user.get());
            
            return ResponseEntity.ok()
                .header("Estado de usuario", "Usuario : "+ user.get().getId() +" Ha sido eliminado")
                .body("Usuario : "+ user.get().getId() + " Ha sido eliminado exitosamente");
            
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Estado de usuario", "Usuario : "+ id +" No encontrado")
                .body("Usuario : "+ id + " No ha sido encontrado");

        }
        
    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id, @RequestBody User user) {
        /**
         * Actualizar los datos de un usuario
         * 
         * --> La actualizacion de un usuario implica actualizar los datos a sus
         *     Entidades que dependan de él.
         */
        
        Optional<User> foundUser = marketUserService.getUserById(id);

        if(marketUserService.updateUser(user, foundUser) == null){

            return ResponseEntity.status(HttpStatus.CONFLICT)
                .header("Estado de usuario", "Usuario : "+ user.getId() +" Ha ocurrido un conflicto")
                .body("Error en el procesamiento de datos");
            
        }else{
            new ResponseEntity<>(user, HttpStatus.CONFLICT);
            return ResponseEntity.ok()
                .header("Estado de usuario", "Usuario : "+ user.getId() +" Actualizado exitosamente")
                .body(user);
        }

    } 
    
    @GetMapping("/allusers/city")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> findByCityLike(@RequestParam(value="name") String city){
        return marketUserService.findByCityLike(city);
    }

    @GetMapping("/allusers/ordereddate")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> findByOrderByRegistration(){
        return marketUserService.findByOrderByRegistration();
    }

    @GetMapping("/allusers/date")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> findByRegistration(@RequestParam(value="name") String date){
        ArrayList<User> usuarios = new ArrayList<>(); 
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        for (User user : marketUserService.findByOrderByRegistration()) {
            String formattedDateTime = user.getRegistration().format(formatter);

            if(formattedDateTime.equals(date)){
                usuarios.add(user);
            }
        }

        return usuarios;

    }

    @GetMapping("/alluserextended")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<User> getUsersWhitDetails() {
        /**
         * Obtener todos los usuarios con sus detalles
         */
        return marketUserService.getUsersWhitDetails();
    }

    
}
