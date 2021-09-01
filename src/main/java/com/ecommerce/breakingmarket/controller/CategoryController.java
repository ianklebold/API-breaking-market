package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.CategoryProduct;
import com.ecommerce.breakingmarket.service.MarketCategoryService;

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
@RequestMapping("/breakingmarket/v1/category")
public class CategoryController {
    @Autowired
    MarketCategoryService marketCategoryService;

    @PostMapping("/newcategory")
    public CategoryProduct newCategory(@RequestBody CategoryProduct category){
        
        return marketCategoryService.newCategory(category);
    }

    @GetMapping("/allcategory")
    public  ArrayList<CategoryProduct> getAllCategories(){
        return marketCategoryService.getAllCategories();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable(name="id") Long id){
        Optional<CategoryProduct> category = marketCategoryService.getCategoryById(id);
        marketCategoryService.deleteCategory(category.get());
    }

    @PutMapping("/update/{id}")
    public CategoryProduct updateCategory(@PathVariable(name = "id") Long id, 
                                          @RequestBody CategoryProduct category){


        Optional<CategoryProduct> foundcategory = marketCategoryService.getCategoryById(id);
        return marketCategoryService.updateCategory(category, foundcategory.get());                               
    }

}
