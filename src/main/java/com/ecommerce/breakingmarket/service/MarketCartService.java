package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.entity.Product;
import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.repository.CartRepository;
import com.ecommerce.breakingmarket.repository.LineProductRepository;
import com.ecommerce.breakingmarket.repository.ProductRepository;
import com.ecommerce.breakingmarket.repository.UserRepository;
import com.ecommerce.breakingmarket.utils.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketCartService {
 
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LineProductRepository lineProductRepository;


    /* METHODS FOR CART!!! */

    public Cart newCart(Cart cart){
        /**
         * Creacion de un nuevo carrito
         */
        
        if(cart.getEnumState().equals(EnumState.ACTIVE)){
            /**
             * Si el nuevo carrito pasa al estado cerrado
             */
            if(cart.getLineProducts().size() == 0){
                /**
                 * No existe carrito cerrado sin productos
                 */
                return null;
            }else{
                /**
                 * Se suma todo y se guarda
                 */
                for(LineProduct line : cart.getLineProducts()){
                    line.setCart(cart);
                }
                sumTotal(cart);
                return cartRepository.save(cart);
            }
        }else{
            /**
             * Si el carrito esta en progreso
             * Controlamos que solo haya un carrito en progreso
             */

            if(controlStateCart(cart) == true){
                /**
                 * Si solo hay uno solo, se suma todo y se guarda
                 */

                for(LineProduct line : cart.getLineProducts()){
                    line.setCart(cart);
                }

                sumTotal(cart);
                return cartRepository.save(cart);
            }else{
                /**
                 * Sino no se guarda
                 */
                return null;
            }
            
        }
        
        
    }

    public Boolean controlStateCart(Cart cart){
        /**
         * Se encarga del control del carrito
         */
        Boolean key = true;
        Integer count = 0;
        
        User user = userRepository.findById(cart.getUser().getId()).get();

        if(user.getCart().size() ==  0){
            /**
             * Si es el primer carrito del usuario, todo bien.
             */
            
            return key;
        }else{
            for (Cart carritos  : user.getCart()) {
                /**
                 * Si no es el primero, iteramos buscando si hay carrito "Active"
                 */
                if(carritos.getEnumState().equals(EnumState.ACTIVE)){
                    
                    count = count + 1;
                }
            }
            
        } 
        if(count > 0){
            /**
             * Hay carrito en progreso, retorna falso 
             * */
            key = false;
        }
        return key;
    }

    public void sumTotal(Cart cart){
        //Iteramos y obtenemos el total final
        for (LineProduct line : cart.getLineProducts()) {
            /**
             * Ahora nos aseguramos que se mantenga el precio de lista
             *  y no se lo pueda cambiar
             */
            Product productaux = findProductById(line.getProduct().getId()).get();
            
            line.getProduct().setPrice(productaux.getPrice());
            /**
             * Seteamos el precio final
             */
            cart.setTotal(cart.getTotal() + (line.getProduct().getPrice() * line.getAmount()));
        }
    }

    public Cart updateCart(Cart cart, Cart foundCart){

        if(foundCart.getEnumState().equals(EnumState.ACTIVE)){
            //Solo se admiten cambios en carritos en activo

            
            /**
             * Sumamos el total actual
             */
            cart.setTotal(0.0);
            for(LineProduct line : cart.getLineProducts()){
                line.setCart(cart);
                lineProductRepository.save(line);
                
            }
            sumTotal(cart);
            //La fecha y usuario no se puede cambiar
            cart.setRegistration(foundCart.getRegistration());
            cart.setUser(foundCart.getUser());

            if(cart.getEnumState().equals(EnumState.CLOSED) && cart.getLineProducts().size() == 0){
                /**
                 * Si actualizamos estado y sacamos todos los productos retornamos nulo o error
                 */
                return null;
            }else{
                /**
                 * Se guardan los cambios
                 */
                System.out.println("Antes de actualizar!!!");
                return cartRepository.save(cart);
            }            
        }else{
            //Sino no se realizan cambios
            return cartRepository.save(foundCart);
        }
    }

    // Metodos para peticiones!!!
    public Cart getAllLinesByCart(Long id){
        return cartRepository.findById(id).get();
    }


    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public ArrayList<Cart> getAllCartsWhitBooks(){
        return (ArrayList<Cart>) cartRepository.findAll();
    }


    public ArrayList<Cart> getAllCarts(){
        return (ArrayList<Cart>) cartRepository.findAll();

    }

    public Optional<Cart> getCartById(Long id){
        return cartRepository.findById(id);
    }

    public void deleteCart(Cart cart){
        cartRepository.delete(cart);
    }
}
