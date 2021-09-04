package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.Invoice;
import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.service.MarketCartService;
import com.ecommerce.breakingmarket.service.MarketInvoiceService;
import com.ecommerce.breakingmarket.utils.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> newInvoice(@PathVariable(name = "iduser") Long iduser,
                                       @PathVariable(name = "idcart") Long idcart,
                                       @RequestBody Invoice invoice){
                                           
        Cart foundCart = marketCartService.getCartById(idcart).get();

        if(iduser.equals(foundCart.getUser().getId())){
            

            if(foundCart.getLineProducts().size() == 0){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Estado de carrito", "Carrito : "+ idcart +" No se puede completar solicitud")
                    .body("El carrito no se puede cerrar sin productos" );
            }else{
                invoice.setCart(foundCart);
                invoice.getCart().setEnumState(EnumState.CLOSED);
                marketInvoiceService.newInvoice(invoice);

                return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Estado de carrito", "Carrito : "+ idcart +" Se completo correctamente la solicitud")
                    .body(invoice);
            }
            
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("Estado de carrito", "Carrito : "+ idcart +" No se puede completar solicitud")
                .body("El carrito no pertenece al id : "+ iduser);
        }
        
    }

    @GetMapping("/allinvoice")
    public ArrayList<Invoice> getAllInvoice(){
        return marketInvoiceService.getAllInvoice();
    }

    @GetMapping("/alllines/{id}")
    public ArrayList<LineProduct> getAllLineProducts(@PathVariable Long id){
        return marketInvoiceService.getAllLineProducts(id);
    }



}
