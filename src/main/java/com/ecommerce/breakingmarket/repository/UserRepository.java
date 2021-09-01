package com.ecommerce.breakingmarket.repository;

import com.ecommerce.breakingmarket.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
}
