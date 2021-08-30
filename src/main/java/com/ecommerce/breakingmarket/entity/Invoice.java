package com.ecommerce.breakingmarket.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Invoice {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @CreationTimestamp
    @Column(name = "date_of_issue", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate = LocalDateTime.now();

    @NotEmpty
    @Column(name = "observation", updatable = true, nullable = false, length=20)
    @Size(max=20)
    private String observation;

    /*Relationship!! */

    @OneToOne(cascade = {})
    private Cart cart;

    /*Constructors!! */

    public Invoice(){}
    
    public Invoice(Long id, LocalDateTime creationDate, @Size(max = 20) String observation, Cart cart) {
        this.id = id;
        this.creationDate = creationDate;
        this.observation = observation;
        this.cart = cart;
    }

    /*Methods!! */

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
     * @return LocalDateTime return the creationDate
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return String return the observation
     */
    public String getObservation() {
        return observation;
    }

    /**
     * @param observation the observation to set
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

}
