package com.ecommerce.breakingmarket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.ecommerce.breakingmarket.utils.EnumGeneratedBy;
import com.ecommerce.breakingmarket.utils.EnumState;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "generatedby", nullable = false, updatable = true)
    @Enumerated(value = EnumType.STRING)
    private EnumGeneratedBy enumGeneratedBy;

    @NotEmpty
    @Column(name = "state", nullable = false, updatable = true)
    @Enumerated(value = EnumType.STRING)
    private EnumState enumState;

    @NotEmpty
    @CreationTimestamp
    @Column(name = "date_of_issue", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registration = LocalDateTime.now();

    @Column(name="total", nullable=false, updatable = true)
    private Double total = 0.0;

    /*--Relationship--!!*/
    
    @JsonBackReference
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<LineProduct> lineProducts = new ArrayList<>();

    /*--Constructors--!!*/

    public Cart(){}
    
    public Cart(Long id, @NotEmpty EnumGeneratedBy enumGeneratedBy, @NotEmpty EnumState enumState,
    @NotEmpty LocalDateTime registration, Double total, User user, List<LineProduct> lineProducts) {
        this.id = id;
        this.enumGeneratedBy = enumGeneratedBy;
        this.enumState = enumState;
        this.registration = registration;
        this.total = total;
        this.user = user;
        this.lineProducts = lineProducts;
    }


    /*--Methods--!!*/
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
     * @return EnumGeneratedBy return the enumGeneratedBy
     */
    public EnumGeneratedBy getEnumGeneratedBy() {
        return enumGeneratedBy;
    }

    /**
     * @param enumGeneratedBy the enumGeneratedBy to set
     */
    public void setEnumGeneratedBy(EnumGeneratedBy enumGeneratedBy) {
        this.enumGeneratedBy = enumGeneratedBy;
    }

    /**
     * @return EnumState return the enumState
     */
    public EnumState getEnumState() {
        return enumState;
    }

    /**
     * @param enumState the enumState to set
     */
    public void setEnumState(EnumState enumState) {
        this.enumState = enumState;
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

    /**
     * @return Double return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return List<LineProduct> return the lineProducts
     */
    public List<LineProduct> getLineProducts() {
        return lineProducts;
    }

    /**
     * @param lineProducts the lineProducts to set
     */
    public void setLineProducts(List<LineProduct> lineProducts) {
        this.lineProducts = lineProducts;
    }

}
