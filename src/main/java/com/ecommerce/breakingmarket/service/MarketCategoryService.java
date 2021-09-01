package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.CategoryProduct;
import com.ecommerce.breakingmarket.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketCategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryProduct newCategory(CategoryProduct category){
        return categoryRepository.save(category);
    }

    public CategoryProduct updateCategory(CategoryProduct category, 
                                         CategoryProduct foundcategory){
        
        category.setId(foundcategory.getId());   
        return categoryRepository.save(category);                             
    }

    public void deleteCategory(CategoryProduct category){
        /**
         * Primero hacemos una limpieza, eliminando el campo de los productos
         * con la categoria eliminada.
         * 
         * */ 
        
        categoryRepository.delete(category);


    }

    public ArrayList<CategoryProduct> getAllCategories(){
        return (ArrayList<CategoryProduct>) categoryRepository.findAll();
    }

    public Optional<CategoryProduct> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

}
