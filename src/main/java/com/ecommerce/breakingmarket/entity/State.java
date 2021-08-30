package com.ecommerce.breakingmarket.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false, updatable = true)
    private String name;
    
    /*Relationship !!*/

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<City> Cities = new ArrayList<>();

    /*Constructors!! */

    public State(Long id, String name, List<City> cities) {
        this.id = id;
        this.name = name;
        Cities = cities;
    }

    public State(){}

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
     * @return List<City> return the Cities
     */
    public List<City> getCities() {
        return Cities;
    }

    /**
     * @param Cities the Cities to set
     */
    public void setCities(List<City> Cities) {
        this.Cities = Cities;
    }

}
