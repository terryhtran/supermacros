//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

public class MyObject {
    public String name;
    public Integer calories;
    public Integer fats;
    public Integer carbs;
    public Integer protein;
    public Integer servingSize;

    // constructor
    public MyObject(String objectName){
        this.name = objectName;
        this.calories = 100;
        this.fats = 101;
        this.carbs = 102;
        this.protein = 103;
        this.servingSize = 104;
    }

    // constructor
    public MyObject(String name, Integer calories, Integer fats, Integer carbs, Integer protein, Integer servingSize){
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
        this.servingSize = servingSize;
    }
}