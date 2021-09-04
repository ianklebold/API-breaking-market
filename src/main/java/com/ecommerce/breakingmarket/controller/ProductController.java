package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Product;
import com.ecommerce.breakingmarket.service.MarketProductService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingmarket/v1/product")
public class ProductController {
    @Autowired
    MarketProductService marketProductService;

    @PostMapping("/newproduct")
    public ResponseEntity<Product> newProduct(@RequestBody Product product){
        /**
         * Crear un nuevo producto
         */
        

        if(marketProductService.newProduct(product) == null ){
            return new ResponseEntity<>(product, HttpStatus.EXPECTATION_FAILED);
        }else{
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }


    }

    @GetMapping("/allproduct")
    @ResponseStatus(HttpStatus.OK)
    public  ArrayList<Product> getAllProducts(){
        /**
         * Obtenemos todos los productos
         */
        return marketProductService.getAllProducts();
    }

    @GetMapping("/allproductpublishied")
    @ResponseStatus(HttpStatus.OK)
    public  ArrayList<Product> getAllProductsPublished(){
        /**
         * Obtenemos todos los productos solo publicados
         */
        return marketProductService.getAllProductsPublished();
    }

    @GetMapping("/allproductnotpublishied")
    @ResponseStatus(HttpStatus.OK)
    public  ArrayList<Product> getAllProductsNotPublished(){
        /**
         * Obtenemos todos los productos no publicados
         */
        return marketProductService.getAllProductsNotPublished();
    }
    
    @GetMapping("/allproductpublishied/orderprice")
    @ResponseStatus(HttpStatus.OK)
    public  ArrayList<Product> findByPublishedTrueOrderByPriceDesc(){
        /**
         * Obtenemos todos los productos publicados y por orden de precios
         */
        return marketProductService.findByPublishedTrueOrderByPriceDesc();
    }

    @GetMapping("/allproductpublishied/orderdate")
    @ResponseStatus(HttpStatus.OK)
    public  ArrayList<Product> findByPublishedTrueOrderByRegistrationDesc(){
        /**
         * Obtenemos todos los productos publicados y por orden de fecha
         */
        return marketProductService.findByPublishedTrueOrderByRegistrationDesc();
    }

    @GetMapping("/allproduct/product")
    @ResponseStatus(HttpStatus.OK)
    public  ArrayList<Product> findByNameContaining(@RequestParam(value="name") String name){
        /**
         * Obtenemos productos que contengan una palabra
         */
        return marketProductService.findByNameContaining(name);
    }
    

    @GetMapping("/allproduct/inventorycode")
    public  ResponseEntity<?> getByInventoryCode(@RequestParam(value="name") String inventorycode){
        /**
         * Obtenemos un producto por su codigo de inventario
         */
        Optional<Product> product = marketProductService.getByInventoryCode(inventorycode);

        if(product.isPresent()){

            return ResponseEntity.ok()
                .header("Estado de producto", "Producto : "+ product.get().getId() +" Se encontro exitosamente")
                .body(product.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Estado de producto", "Codigo : "+ inventorycode +" No encontrado")
                .body("Producto de codigo : "+ inventorycode + " No ha sido encontrado");
        }
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name="id") Long id){

        Optional<Product> product = marketProductService.getProductById(id);

        if(product.isPresent()){
            marketProductService.deleteProduct(product.get());
            return new ResponseEntity<>("Producto "+id+" Eliminado con exito", 
                                        HttpStatus.OK);
        }else{
            return new ResponseEntity<>("ERROR : 404 Producto "+id+" No encontrado", 
                                        HttpStatus.NOT_FOUND);
        }


    }

    @PutMapping("/update/{id}")
    public  ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long id, 
    @RequestBody Product product){
        
        Optional<Product> foundproduct = marketProductService.getProductById(id);
        product.setId(id);




        if(marketProductService.updateProduct(product, foundproduct)== null){
            
            return new ResponseEntity<>(product, HttpStatus.EXPECTATION_FAILED);
        }else{
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    
    
    }

    @GetMapping("/bycategory")
    public  ArrayList<Product> getByCategory(@RequestParam(value="name") String category){
        /**
         * Obtenemos una lista de productos dada una categoria
         */
        return marketProductService.getProductsByCategory(category);
        
    }

    


}
