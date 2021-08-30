package com.ecommerce.breakingmarket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false, updatable = true)
    private String name; 
    
    @NotEmpty
    @Column(name = "surname", nullable = false, updatable = true)
    private String surname;

    @NotEmpty
    @Column(name = "email", nullable = false, updatable = true, unique = true)
    private String email;

    @NotEmpty
    @Column(name = "password", nullable = false, updatable = true)
    @Size(min=8)
    private String password;

    @NotEmpty
    @CreationTimestamp
    @Column(name = "registration", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registration = LocalDateTime.now();
    
    /*RelationShip!!! */
    @JsonManagedReference
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Cart> cart = new ArrayList<Cart>();

    @ManyToOne(cascade={})
    private City city;

    @ManyToOne(cascade={})
    private Country country;

    @ManyToOne(cascade={})
    private State state;

    /*Constructors!!! */

    public User(Long id, String name, String surname, String email, @Size(min = 8) String password,
    LocalDateTime registration, List<Cart> cart, City city, Country country, State state) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.registration = registration;
        this.cart = cart;
        this.city = city;
        this.country = country;
        this.state = state;
    }

    public User(){}
    


    /*Methods!!*/
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
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return String return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return String return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return LocalDateTime return the registration
     */
    public LocalDateTime getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

}
