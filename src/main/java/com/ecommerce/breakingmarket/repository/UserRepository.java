package com.ecommerce.breakingmarket.repository;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
    ArrayList<User> findByCityLike(String city);
    ArrayList<User> findByOrderByRegistration();
    Optional<User> findByCart(Cart cart);
}
