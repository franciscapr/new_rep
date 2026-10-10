package com.duoc.backend.care;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Care {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double cost;

<<<<<<< HEAD
    // Constructor vacío requerido por JPA
    public Care() {
    }

=======
>>>>>>> cee53d6f20b35fd1d96dc9c997ecd9c0e6fac3fb
    public Care(String description, double cost) {
        this.name = description;
        this.cost = cost;
    }

<<<<<<< HEAD
=======
    // Getters and Setters
>>>>>>> cee53d6f20b35fd1d96dc9c997ecd9c0e6fac3fb
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}