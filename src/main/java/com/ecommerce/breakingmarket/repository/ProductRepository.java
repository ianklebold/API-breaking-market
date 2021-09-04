package com.ecommerce.breakingmarket.repository;

import java.util.ArrayList;

import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    ArrayList<Product> findByPublishedTrue();
    ArrayList<Product> findByPublishedFalse();
    ArrayList<Product> findByNameContaining(String name);
    Optional<Product> findByInventoryCode(String inventoryCode);
    ArrayList<Product> findByPublishedTrueOrderByPriceDesc();
    ArrayList<Product> findByPublishedTrueOrderByRegistrationDesc();
}
