package com.oopclass.breadapp.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Entity
@Table(name = "pricelist")
public class Pricelist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id"+ "created", updatable = false, nullable = false)
	   private long id;

    private String productName;

    private String type;

    private Double price;

    private LocalDate created;

    private LocalDate updated;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        LocalDate localDateTime = LocalDate.now();
        this.created = localDateTime;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        LocalDate localDateTime = LocalDate.now();
        this.updated = localDateTime;
    }

	
	

	
	
	
	

	@Override
	public String toString() {
		return "User [id=" + id + ", productName=" + productName +  "]";
	}

	
}
