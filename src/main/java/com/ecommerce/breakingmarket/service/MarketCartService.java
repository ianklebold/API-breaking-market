package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.Invoice;
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

    public Integer newCart(Cart cart){
        /**
         * Creacion de un nuevo carrito
         */
        int flag = 0;
        if(cart.getEnumState().equals(EnumState.CLOSED)){
            /**
             * Carrito no puede pasar a estado cerrado desde este
             * EndPoint
             */
            return -1;
        }else{
            /**
             * Si el carrito esta en progreso
             * Controlamos que solo haya un carrito en progreso
             */
            if(controlStateCart(cart) == true){
                /**
                 * Ahora controlamos que solo haya producto publicados
                 */
                for(LineProduct line : cart.getLineProducts()){
                    if(line.getProduct().getPublished().equals(false)){
                        flag = flag + 1;
                    }
                }
                if(flag == 0){
                    //Ningun problema en los productos
                    //Se suma todo y se guarda
                    for(LineProduct line : cart.getLineProducts()){
                        line.setCart(cart);
                    }
                    sumTotal(cart);
                    cartRepository.save(cart);
                    return 0;
                }else{
                    //Retorno de error.
                    return -2;
                }

            }else{
                /**
                 * Tiene mas de un carrito activo
                 */
                return -3;
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

    public Integer updateCart(Cart cart, Cart foundCart){
        int flag = 0;
        if(foundCart.getEnumState().equals(EnumState.ACTIVE)){
            //Solo se admiten cambios en carritos en activo

            
            /**
             * Sumamos el total actual
             */

            for(LineProduct line : cart.getLineProducts()){
                if(line.getProduct().getPublished().equals(false)){
                    flag = flag + 1;
                }
            }

            if(flag == 0){
                cart.setTotal(0.0);
                //Ningun problema en los productos
                for(LineProduct line : cart.getLineProducts()){
                    line.setCart(cart);
                    lineProductRepository.save(line);
                }
                sumTotal(cart);
                //La fecha y usuario no se puede cambiar
                cart.setRegistration(foundCart.getRegistration());
                cart.setUser(foundCart.getUser());
            }else{
                //Retorno de error.
                return -1;
            }

            if(cart.getEnumState().equals(EnumState.CLOSED)){
                /**
                 * Si actualizamos estado y sacamos todos los productos retornamos nulo o error
                 */
                return -2;
            }else{
                /**
                 * Se guardan los cambios
                 */
                cartRepository.save(cart);
                return 0;
            }            
        }else{
            //Sino no se realizan cambios
            return -2;
        }
    }

    // Metodos para peticiones!!!
    public Cart getAllLinesByCart(Long id){
        return cartRepository.findById(id).get();
    }


    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }


    public ArrayList<Cart> getAllCarts(){
        return (ArrayList<Cart>) cartRepository.findAll();

    }

    public ArrayList<Cart> findByEnumState(EnumState enumState){
        return (ArrayList<Cart>) cartRepository.findByEnumState(enumState);

    }

    public ArrayList<Cart> findByUser(Long id){

        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return (ArrayList<Cart>) cartRepository.findByUser(user.get());
        }else{
            return null;
        }
        

    }

    public ArrayList<Cart> findByUserAndState(Long id, EnumState enumState){
        ArrayList<Cart> carts = new ArrayList<Cart>();

        if(findByUser(id) != null){
            for (Cart cart : findByUser(id)) {
                if(cart.getEnumState().equals(enumState)){
                    carts.add(cart);
                }
            }
            return carts;
        }else{
            return null;
        }
    }


    public Optional<Cart> getCartById(Long id){
        return cartRepository.findById(id);
    }

    public void deleteCart(Cart cart){
        cartRepository.delete(cart);
    }
}
