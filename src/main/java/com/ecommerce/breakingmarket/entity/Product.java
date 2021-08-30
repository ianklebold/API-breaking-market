package com.ecommerce.breakingmarket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @Column(name = "name", nullable = false, updatable = true)
    private String name;
    
    @NotEmpty
    @Column(name = "price", nullable = false, updatable = true)
    private Double price;

    @NotEmpty
    @Column(name = "inventorycode", nullable = false, updatable = false, unique = true)
    private String inventoryCode;

    @NotEmpty
    @Column(name = "description", nullable = false, updatable = true)
    private String description;
    
    @Column(name = "content", nullable = true, updatable = true)
    private String content;

    @Column(name = "published", nullable = true, updatable = true)
    private Boolean published;

    @NotEmpty
    @CreationTimestamp
    @Column(name = "date_of_issue", updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registration = LocalDateTime.now();

    /*Relationsip!!!*/

    @ManyToMany
    @JoinTable(
    name = "product_whit_category", 
    joinColumns = @JoinColumn(name = "product_id"), 
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<CategoryProduct> categories = new ArrayList<CategoryProduct>();

    /*Constructors!!!*/
    public Product(){}

    public Product(Long id, String name, Double price, String inventoryCode, String description, String content,
            Boolean published, LocalDateTime registration, List<CategoryProduct> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inventoryCode = inventoryCode;
        this.description = description;
        this.content = content;
        this.published = published;
        this.registration = registration;
        this.categories = categories;
    }
    


    /*Methods!!!*/

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
     * @return Double return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return String return the inventoryCode
     */
    public String getInventoryCode() {
        return inventoryCode;
    }

    /**
     * @param inventoryCode the inventoryCode to set
     */
    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return Boolean return the published
     */
    public Boolean isPublished() {
        return published;
    }

    /**
     * @param published the published to set
     */
    public void setPublished(Boolean published) {
        this.published = published;
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
     * @return List<CategoryProduct> return the cart
     */
    public List<CategoryProduct> getCart() {
        return categories;
    }

    /**
     * @param categories the cart to set
     */
    public void setCart(List<CategoryProduct> categories) {
        this.categories = categories;
    }

}
