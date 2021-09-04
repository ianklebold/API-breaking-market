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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> cartNewCart(@PathVariable(name="id") Long id ,@RequestBody Cart cart){
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
            Integer result = marketCartService.newCart(cart);
            if(result.equals(-1)){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Estado de carrito", "Usuario : "+ id +" Ha ocurrido un conflicto")
                .body("Error no se puede cerrar el carrito, intente desde /breakingmarket/v1/invoice/closecart");

            }else{
                if(result.equals(-2)){

                    return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Estado de carrito", "Usuario : "+ id +" Ha ocurrido un conflicto")
                    .body("Error no se puede crear carrito porque hay productos añadidos que no estan disponibles o no existen, porfavor acceda al catalogo de productos");

                }else{
                    if(result.equals(-3)){

                        return ResponseEntity.status(HttpStatus.CONFLICT)
                        .header("Estado de carrito", "Usuario : "+ id +" Ha ocurrido un conflicto")
                        .body("Error no se puede crear carrito porque existe dos en estado ACTIVO, porfavor acceda a su listado de carritos");

                    }else{
                        

                        return ResponseEntity.status(HttpStatus.CREATED)
                        .header("Estado de carrito", "Usuario : "+ id +" Carrito creado con exito")
                        .body("Carrito guardado.");
                    }
                }
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .header("Estado de carrito", "Usuario : "+ id +" Ha ocurrido un conflicto")
            .body("Error no se encuntra el usuario : " + id);

        }

    }

    @GetMapping("/allcart")
    public ArrayList<Cart> getAllCarts() {
        /**
         * Obtener todos los carritos
         */
        return marketCartService.getAllCarts();
    }

    @DeleteMapping("/user/delete/{iduser}/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable(name="id") Long id, @PathVariable(name="iduser") Long iduser ){
        /**
         * Eliminamos un carrito
         */
        Optional<Cart> cart = marketCartService.getCartById(id);
        if(iduser.equals(cart.get().getUser().getId())){
            marketCartService.deleteCart(cart.get());

            return ResponseEntity.ok()
                .header("Estado de carrito", "Carrito : "+ id +" Se ha eliminado correctamente")
                .body("El carrito se ha eliminado satisfactoriamente"); 
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("Estado de carrito", "Carrito : "+ id +" No se puede completar solicitud")
                .body("El carrito no pertenece al id : "+ iduser);
        }
         
    }  

    @PutMapping("/user/update/{iduser}/{id}")
    public ResponseEntity<?> updateCart(@PathVariable(name="id") Long id, 
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
            Integer result = marketCartService.updateCart(cart, foundCart);
            if(result.equals(-1)){

                
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Estado de carrito", "Usuario : "+ id +" Ha ocurrido un conflicto")
                    .body("Error no se puede crear carrito porque hay productos añadidos que no estan disponibles o no existen, porfavor acceda al catalogo de productos");
            }else{
                if(result.equals(-2)){

                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .header("Estado de carrito", "Usuario : "+ id +" Ha ocurrido un conflicto")
                        .body("Error no se puede cerrar el carrito, intente desde /breakingmarket/v1/invoice/closecart");

                }else{
                        //FoundCart para este punto ya es igual a cart, por lo tanto retorna el carrito actualizado
                        return ResponseEntity.status(HttpStatus.CREATED)
                        .header("Estado de carrito", "Usuario : "+ id +" Carrito creado con exito")
                        .body(foundCart);
                }
            }
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("Estado de carrito", "Carrito : "+ id +" No se puede completar solicitud")
                .body("El carrito no pertenece al id : "+ iduser);
        }
    }

    @GetMapping("/{id}/alllines")
    public List<LineProduct> getAllLinesByCart(@PathVariable Long id){

        return marketCartService.getAllLinesByCart(id).getLineProducts();


    }




}
