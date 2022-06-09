package hska.iwi.eShopMaster.model.database.dataobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Product {
    private int id;

    private String name;

    private double price;

    @JsonIgnore
    private Category category;

    private String details;

    public Product() {
    }

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product(String name, double price, Integer categoryId, String details) {
        this.name = name;
        this.price = price;
        this.category = new Category();
        this.category.setId(categoryId);
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return this.category.getId();
    }

    public void setCategoryId(Integer categoryId) {
        this.category = new Category(categoryId);
    }

    @JsonIgnore
    public Category getCategory() {
        return category;
    }

    @JsonIgnore
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}