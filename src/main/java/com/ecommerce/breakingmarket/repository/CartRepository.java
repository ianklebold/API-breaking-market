package com.ecommerce.breakingmarket.repository;

import com.ecommerce.breakingmarket.entity.Cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>{
    
}
