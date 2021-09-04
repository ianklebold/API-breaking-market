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
    public  ArrayList<Product> getAllCategories(){
        /**
         * Obtenemos todos los libros
         */
        return marketProductService.getAllProducts();
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

    


}
