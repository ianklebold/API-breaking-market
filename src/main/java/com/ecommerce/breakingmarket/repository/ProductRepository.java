package com.ecommerce.breakingmarket.repository;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    ArrayList<Product> findByPublishedTrue();
}
