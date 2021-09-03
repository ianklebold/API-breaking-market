package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.service.MarketCartService;
import com.ecommerce.breakingmarket.service.MarketProductService;
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
@RequestMapping("/breakingmarket/v1/cart")
public class CartController {
    @Autowired
    MarketUserService marketUserService;

    @Autowired
    MarketProductService marketProductService;

    @Autowired
    MarketCartService marketCartService;

    @PostMapping("/{id}/newcart")
    public Cart cartNewCart(@PathVariable(name="id") Long id ,@RequestBody Cart cart){
        /**
         * Creacion de un nuevo carrito.
         *  -> Se puede crear un carrito, cargar los productos y cerrarlo.
         *  -> No se permiten cerrar carritos sin productos.
         *  -> Se admite solo un carrito en proceso por usuario
         *  -> Se admiten multiples carritos cerrados por usuarios.
         * 
         */
        Optional<User>  user = marketUserService.getUserById(id);

        if(user.isPresent()){
            cart.setUser(user.get());
            return marketCartService.newCart(cart);
        }else{
            return null;
        }

    }

    @GetMapping("/allcart")
    public ArrayList<Cart> getAllCarts() {
        /**
         * Obtener todos los carritos
         */
        return marketCartService.getAllCarts();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCart(@PathVariable(name="id") Long id ){
        /**
         * Eliminamos un carrito
         */
        Optional<Cart> cart = marketCartService.getCartById(id);
        marketCartService.deleteCart(cart.get()); 
    }  

    @PutMapping("/user/{iduser}/update/{id}")
    public Cart updateCart(@PathVariable(name="id") Long id, 
                           @PathVariable(name="iduser") Long iduser,
                            @RequestBody Cart cart) {
        /**
         * Actualizacion de carrito.
         *  -> Se puede aumentar la cantidad de productos y decrementar la cantidad de productos
         *  -> Se puede cargar mas de una vez un producto (Varias compras de un mismo producto)
         *  -> Al aumentar productos o decrementar se actualiza automaticamente el precio total 
         *  
         *  **Nota : Solo se admite actualizacion de carritos activos.
         */
        Cart foundCart = marketCartService.getCartById(id).get();


        if(iduser.equals(foundCart.getUser().getId())){
            //El dueño del carrito quiere actualizar su propio carrito.
            cart.setId(id);
            return marketCartService.updateCart(cart, foundCart);
        }else{
            return null;
        }
    }

    @GetMapping("/{id}/alllines")
    public List<LineProduct> getAllLinesByCart(@PathVariable Long id){

        return marketCartService.getAllLinesByCart(id).getLineProducts();


    }




}
