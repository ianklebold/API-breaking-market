package com.ecommerce.breakingmarket.repository;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.utils.EnumState;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>{
    
    ArrayList<Cart> findByEnumState(EnumState enumState);

    ArrayList<Cart> findByUser(User user);

}
