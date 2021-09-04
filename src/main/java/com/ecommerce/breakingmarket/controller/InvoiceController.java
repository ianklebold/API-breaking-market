package com.ecommerce.breakingmarket.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.ecommerce.breakingmarket.entity.Cart;
import com.ecommerce.breakingmarket.entity.Invoice;
import com.ecommerce.breakingmarket.entity.LineProduct;
import com.ecommerce.breakingmarket.entity.User;
import com.ecommerce.breakingmarket.service.MarketCartService;
import com.ecommerce.breakingmarket.service.MarketInvoiceService;
import com.ecommerce.breakingmarket.service.MarketUserService;
import com.ecommerce.breakingmarket.utils.EnumState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/breakingmarket/v1/invoice")
public class InvoiceController{

    @Autowired
    MarketInvoiceService marketInvoiceService; 
    
    @Autowired
    MarketUserService marketUserService; 

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
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Invoice> getAllInvoice(){
        return marketInvoiceService.getAllInvoice();
    }

    @GetMapping("/allinvoice/user/{iduser}/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id, @PathVariable Long iduser){
        Optional<Invoice> invoice = marketInvoiceService.getInvoiceById(id);
        
        if(invoice.isPresent()){
            if(iduser == invoice.get().getCart().getUser().getId()){
                return ResponseEntity.ok()
                    .header("Estado de orden", "Orden : "+ invoice.get().getId() +" Ha sido encontrada")
                    .body(invoice.get());
                }else{
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .header("Estado de orden", "Id Usuario : "+ iduser +" No tiene permiso")
                        .body("No tiene permiso para ver esta informacion");
                }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Estado de orden", "Orden : "+ id +" No encontrado")
                .body("Orden : "+ id + " No ha sido encontrado");
        }
    }

    @GetMapping("/allinvoice/user/{iduser}")
    public ResponseEntity<?> getInvoiceByUser(@PathVariable Long iduser){
        Optional<User> user = marketUserService.getUserById(iduser);
        
        if(user.isPresent()){
                return ResponseEntity.ok()
                    .header("Estado de orden", "Usuario de orden : "+ user.get().getId() +" Ha sido encontrada")
                    .body(marketInvoiceService.getAllInvoiceByUser(user.get()));
                
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Estado de orden", "Usuario de orden : "+ iduser +" No encontrado")
                .body("Orden : "+ iduser + " No ha sido encontrado");
        }
    }

    

    @GetMapping("/alllines/{id}")
    public ArrayList<LineProduct> getAllLineProducts(@PathVariable Long id){
        return marketInvoiceService.getAllLineProducts(id);
    }



}
