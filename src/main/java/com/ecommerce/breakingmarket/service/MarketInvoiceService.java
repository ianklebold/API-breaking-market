package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Invoice;
import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.repository.InvoiceRepository;
import com.ecommerce.breakingmarket.repository.LineProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class MarketInvoiceService {
    
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    LineProductRepository lineProductRepository;

    public Invoice newInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    public Invoice updateCategory(Invoice invoice, Invoice foundinvoice){
        
        invoice.setId(foundinvoice.getId());   
        invoice.setCreationDate(foundinvoice.getCreationDate());
        invoice.setCart(foundinvoice.getCart());
        
        return invoiceRepository.save(invoice);                             
    }

    public ArrayList<Invoice> getAllInvoice(){
        return (ArrayList<Invoice>) invoiceRepository.findAll();
    }

    public ArrayList<Invoice> getAllInvoiceByUser(User user){

        return (ArrayList<Invoice>) invoiceRepository.findByCartIn(user.getCart());
    }

    public Optional<Invoice> getInvoiceById(Long id){
        return invoiceRepository.findById(id);
    }


    public ArrayList<LineProduct> getAllLineProducts(Long id){
        return (ArrayList<LineProduct>) lineProductRepository.findByCart(id);
    }

    




}
