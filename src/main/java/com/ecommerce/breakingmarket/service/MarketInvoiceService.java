package com.ecommerce.breakingmarket.service;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.Invoice;
import com.ecommerce.breakingmarket.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketInvoiceService {
    
    @Autowired
    InvoiceRepository invoiceRepository;

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

    




}
