//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

public class MyEntry {
    public String name;
    public Integer calories;
    public Integer fats;
    public Integer carbs;
    public Integer protein;
    public Integer servings;

    // constructor
    public MyEntry(String name){
        this.name = name;
        this.calories = 0;
        this.fats = 0;
        this.carbs = 0;
        this.protein = 0;
        this.servings = 0;
    }

    // constructor
    public MyEntry(String name, Integer calories, Integer fats, Integer carbs, Integer protein, Integer servings){
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
        this.servings = servings;
    }

    public void timesServings() {
        this.calories = this.calories * this.servings;
        this.fats = this.fats * this.servings;
        this.carbs = this.carbs * this.servings;
        this.protein = this.protein * this.servings;
        return;
    }

    public void divideServings() {
        this.calories = this.calories / this.servings;
        this.fats = this.fats / this.servings;
        this.carbs = this.carbs / this.servings;
        this.protein = this.protein / this.servings;
        return;
    }
}