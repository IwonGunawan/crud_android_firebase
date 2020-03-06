package com.crud.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class ItemModel implements Serializable {

    private String name;
    private String brand;
    private String price;
    private String key;

    public ItemModel() {

    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " " + name + "\n" +
                " " + brand + "\n" +
                " " + price;
    }

    public ItemModel(String nm, String br, String pe) {
        name    = nm;
        brand   = br;
        price   = pe;
    }


}
