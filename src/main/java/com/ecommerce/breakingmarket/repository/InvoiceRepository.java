package com.ecommerce.breakingmarket.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.Invoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{
    

    ArrayList<Invoice> findByCartIn(List<Cart> list);
    Optional<Invoice> findById(Long id);
}
