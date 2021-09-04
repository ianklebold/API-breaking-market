package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.entity.Product;
import com.ecommerce.breakingmarket.repository.CartRepository;
import com.ecommerce.breakingmarket.repository.CategoryRepository;
import com.ecommerce.breakingmarket.repository.ProductRepository;
import com.ecommerce.breakingmarket.repository.UserRepository;
import com.ecommerce.breakingmarket.utils.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketProductService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartRepository cartRepository;

    /* METHODS FOR Product!!! */

    public Product newProduct(Product product){
        /**
         * Creacion de un Product
         */
        /*
        if((controlCategory(String.valueOf(product.getCategory()))) == false){
            product.setCategory(null);
            //Error no se puede mandar una categoria distinta.
        }*/

        if(product.getDescription().length()>10){
            return productRepository.save(product);
        }else{
            return null;
        }

        
    }



    public void deleteProduct(Product product){
        ArrayList<Cart> listCarritos = (ArrayList<Cart>) cartRepository.findAll(); 
        
        for (Cart cart : listCarritos) {
            if(cart.getEnumState().equals(EnumState.ACTIVE)){
                //Obtenemos los productos de un carrito.
                List<LineProduct> listaLinea = cart.getLineProducts();

               // List<Book> listaCart = cart.getListBooks();
                
                for (int i = 0; i < listaLinea.size(); i++){
                    Boolean foundIds = false;
                    if(product.getId().equals(listaLinea.get(i).getProduct().getId())){
                        //Controlamos si el producto a eliminar esta en algun carrito
                        foundIds = true;
                    }

                    if(foundIds){
                       /**
                        *Damos de baja el producto de carrito y 
                        *Actualizamos su precio (Only Active) 
                        */
                        cart.setTotal(cart.getTotal() - product.getPrice());
                        //cart.getListBooks().remove(book);

                        cart.getLineProducts().remove(cart.getLineProducts().get(i));
                        
                        cartRepository.save(cart);
                    }
                    
           
                }
            
            }

        }
        //Eliminamos producto
        productRepository.delete(product);
    }

    public Product updateProduct(Product product, Optional<Product> foundProduct){

        if(product.getDescription().length()<10){
            return null;
        }else{
        
            if(product.getPrice() != foundProduct.get().getPrice()){
                /**
                 * Metodo para actualizar precios de productos con producto actualizado
                 */
                updateTotalCartForBooks(product, foundProduct);
            }
            
            //Codigo de producto no se puede cambiar
            product.setInventoryCode(foundProduct.get().getInventoryCode());
            

            return productRepository.save(product);
        }
    }
    
    public void updateTotalCartForBooks(Product product, Optional<Product> foundProduct){
       
        ArrayList<Cart> listCarritos = (ArrayList<Cart>) cartRepository.findAll(); 
        System.out.println(listCarritos);

        for (Cart cart : listCarritos) {
            //Preguntamos si el carrito esta en Activo
            if(cart.getEnumState().equals(EnumState.ACTIVE)){
                //Si es asi obtengo su lista de productos
                List<LineProduct> listaLinea = cart.getLineProducts();
                
                for (int i = 0; i < listaLinea.size(); i++){
                    System.out.println(listaLinea.size());
                    System.out.println(listaLinea.get(i).getProduct().getId());
                    System.out.println(product.getId());
                    Boolean foundIds = false;
                    if(product.getId().equals(listaLinea.get(i).getProduct().getId())){                        
                        //Preguntamos si el producto actulizado esta en la lista
                        
                        foundIds = true;
                    }

                    if(foundIds){
                        System.out.println("Entro a actualizar");
                        if(foundProduct.get().getPrice() > product.getPrice()){

                            //El precio disminuyó, disminuye el precio en cada carrito.
                            cart.setTotal(cart.getTotal() - listaLinea.get(i).getAmount() * foundProduct.get().getPrice());

                            
                            cart.setTotal(cart.getTotal() + listaLinea.get(i).getAmount() * product.getPrice());

                            

                            
                        }else if(foundProduct.get().getPrice() < product.getPrice()){

                            //El precio aumentó, aumenta el precio en cada carrito   100 --> 2(50)  Deberiamos de tener 60 --> 2(30) pero tengo 80
                            System.out.println(cart.getTotal() - listaLinea.get(i).getAmount() * foundProduct.get().getPrice());
                            cart.setTotal(cart.getTotal() - listaLinea.get(i).getAmount() * foundProduct.get().getPrice());
                            
                            System.out.println(cart.getTotal() + listaLinea.get(i).getAmount() * product.getPrice());
                            cart.setTotal(cart.getTotal() + listaLinea.get(i).getAmount() * product.getPrice());

                        }
                        cartRepository.save(cart); 
                    }
                }  
            }

        }
       

    }

    // Metodos para peticiones!!!

    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public ArrayList<Product> getAllProducts(){
        return (ArrayList<Product>) productRepository.findAll();
    }

    public ArrayList<Product> getAllProductsPublished(){
        return (ArrayList<Product>) productRepository.findByPublishedTrue();
    }

    public ArrayList<Product> getAllProductsNotPublished(){
        return (ArrayList<Product>) productRepository.findByPublishedFalse();
    }

    public ArrayList<Product> findByNameContaining(String name){
        return (ArrayList<Product>) productRepository.findByNameContaining(name);
    }

    public ArrayList<Product> findByPublishedTrueOrderByPriceDesc(){
        return (ArrayList<Product>) productRepository.findByPublishedTrueOrderByPriceDesc();
    }

    public ArrayList<Product> findByPublishedTrueOrderByRegistrationDesc(){
        return (ArrayList<Product>) productRepository.findByPublishedTrueOrderByRegistrationDesc();
    }


    public Optional<Product> getByInventoryCode(String inventoryCode){
        return  productRepository.findByInventoryCode(inventoryCode);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    
}
