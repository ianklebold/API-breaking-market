package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.Invoice;
import com.ecommerce.breakingmarket.service.MarketCartService;
import com.ecommerce.breakingmarket.service.MarketInvoiceService;
import com.ecommerce.breakingmarket.utils.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingmarket/v1/invoice")
public class InvoiceController{

    @Autowired
    MarketInvoiceService marketInvoiceService; 
    
    @Autowired
    MarketCartService marketCartService;

    @PostMapping("/closecart/{iduser}/{idcart}")
    public Invoice newInvoice(@PathVariable(name = "iduser") Long iduser,
                                       @PathVariable(name = "idcart") Long idcart,
                                       @RequestBody Invoice invoice){
                                           
        Cart foundCart = marketCartService.getCartById(idcart).get();

        if(iduser.equals(foundCart.getUser().getId())){

            invoice.setCart(foundCart);
            invoice.getCart().setEnumState(EnumState.CLOSED);
            return marketInvoiceService.newInvoice(invoice);
        }else{
            return null;
        }
        
    }

    @GetMapping("/allinvoice")
    public ArrayList<Invoice> getAllInvoice(){
        return marketInvoiceService.getAllInvoice();
    }



}
