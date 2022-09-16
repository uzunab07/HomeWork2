package com.example.homework2;

/*
//Homework 2
//Khaled Mohamed Ali and Joseph Mauney
 */

import java.io.Serializable;

public class Drink implements Serializable {
    double size1, alcoholPercentage;
    String date;

    public Drink(double size, double alcoholPercentage, String date) {
        this.size1 = size;
        this.alcoholPercentage = alcoholPercentage;
        this.date = date;
    }

    public double getSize1() {
        return size1;
    }

    public double getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public String getDate() {
        return date;
    }
}
