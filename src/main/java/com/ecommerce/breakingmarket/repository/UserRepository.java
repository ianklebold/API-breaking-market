package com.ecommerce.breakingmarket.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
    ArrayList<User> findByCityLike(String city);

}
