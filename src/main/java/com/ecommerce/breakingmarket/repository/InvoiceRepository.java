package com.ecommerce.breakingmarket.repository;

import com.ecommerce.breakingmarket.entity.Invoice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long>{
    
}
