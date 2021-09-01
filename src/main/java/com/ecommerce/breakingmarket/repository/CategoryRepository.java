package com.ecommerce.breakingmarket.repository;

import com.ecommerce.breakingmarket.entity.CategoryProduct;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryProduct, Long>{
    
}
