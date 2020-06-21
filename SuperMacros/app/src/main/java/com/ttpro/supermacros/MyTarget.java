//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

public class MyTarget {
    public Integer calories;
    public Integer fats;
    public Integer carbs;
    public Integer protein;

    // constructor
    public MyTarget(){
        this.calories = 2500;
        this.fats = 50;
        this.carbs = 50;
        this.protein = 150;
    }

    // constructor
    public MyTarget(Integer calories, Integer fats, Integer carbs, Integer protein){
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
    }
}