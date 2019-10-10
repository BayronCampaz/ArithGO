package edu.icesi.arithgo.model.entity;

import androidx.annotation.NonNull;

public class Product {

    private String name;
    private int value;

    public Product(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return name + "    " + value +  " puntos" + "         CANJEAR";
    }
}
