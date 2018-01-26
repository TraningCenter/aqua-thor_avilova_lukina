
package com.mycompany.aguathor.controller;

import com.mycompany.aguathor.model.Food;
import java.util.ArrayList;

/**
 *
 * @author Анастасия
 */
public class FoodsController {
    
     /**
      * список всех рыб
      */
    public ArrayList<Food> foods;
    
    /**
     * количество еды
     */
    private int numberOfFoods;
    
    /**
     * максимальный размер поля по x
     */
    private int maxX;
    
    /**
     * максимальный размер поля по y
     */
    private int maxY;
    
    public FoodsController(int numberOfFoods, ArrayList<Food> foods, int maxX, int maxY){
        this.maxX = maxX;
        this.maxY = maxY;
        this.numberOfFoods = numberOfFoods;
        this.foods = foods;
    }
    
    public int getNumberOfFoods() {
        return numberOfFoods;
    }

    public void setNumberOfFoods (int numberOfFoods) {
        this.numberOfFoods = numberOfFoods;
    }
    
    /**
     * обновить список еды
     */
    public void refresh(){
        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getisEaten()){
                foods.get(i).changeLocation(maxX, maxY);
                foods.get(i).setisEaten(false);
            }
        }
    }
}
