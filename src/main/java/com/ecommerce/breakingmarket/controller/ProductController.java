package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Product;
import com.ecommerce.breakingmarket.service.MarketProductService;

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
@RequestMapping("/breakingmarket/v1/product")
public class ProductController {
    @Autowired
    MarketProductService marketProductService;

    @PostMapping("/newproduct")
    public Product newProduct(@RequestBody Product product){
        /**
         * Crear un nuevo producto
         */
        return marketProductService.newProduct(product);
    }

    @GetMapping("/allproduct")
    public  ArrayList<Product> getAllCategories(){
        /**
         * Obtenemos todos los libros
         */
        return marketProductService.getAllProducts();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable(name="id") Long id){
        Optional<Product> product = marketProductService.getProductById(id);
        marketProductService.deleteProduct(product.get());
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable(name = "id") Long id, 
    @RequestBody Product product){
        
        Optional<Product> foundproduct = marketProductService.getProductById(id);
        product.setId(id);
        return marketProductService.updateProduct(product, foundproduct);
    
    
    }

    


}
