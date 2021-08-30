package com.ecommerce.breakingmarket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class LineProduct {
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "amount", nullable = false, updatable = true)
    private int amount;

    /*Relationsip!!!*/
    @OneToOne(cascade = {})
    private Product product;

    @JsonBackReference
    @ManyToOne(cascade = {})
    private Cart cart;

    /*Constructors!!!*/

    LineProduct(){}

        
    public LineProduct(Long id, int amount, Cart cart) {
        this.id = id;
        this.amount = amount;
        this.cart = cart;
    }
    
    /*Methods!!! */

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return int return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return Cart return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * @param cart the cart to set
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
