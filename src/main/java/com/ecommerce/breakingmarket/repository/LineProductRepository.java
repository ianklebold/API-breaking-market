package com.ecommerce.breakingmarket.repository;

import com.ecommerce.breakingmarket.entity.LineProduct;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineProductRepository extends CrudRepository<LineProduct, Long>{
    
}
