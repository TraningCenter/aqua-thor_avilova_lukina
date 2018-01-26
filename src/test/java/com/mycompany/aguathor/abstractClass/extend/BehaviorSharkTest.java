/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor.abstractClass.extend;


import com.mycompany.aguathor.Location;
import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.abstractClass.Behavior;
import com.mycompany.aguathor.controller.FoodsController;
import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.TYPE;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.model.Food;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Анастасия
 */
public class BehaviorSharkTest {
     
     @Test
    public void testFindNearest() {
        int maxX = 105;
        int maxY = 105;
         ArrayList<DIRECTION> matr = new ArrayList<DIRECTION>();
         for (int j = 0; j < 105; j++){
             if (j%2==1)
                 matr.add(DIRECTION.left);
             else
                 matr.add(DIRECTION.right);
         }


         ArrayList<Fish> fishes = new ArrayList<Fish>();
        fishes.add(new Fish(2, TYPE.shark, 2, new Location(10,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(9,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,12), 5, 15, 13, 2, 0));


        
        ArrayList<Food> foods = new ArrayList<Food>();
        foods.add(new Food(8, 10, 0));
        foods.add(new Food(15, 10, 0));
        foods.add(new Food(100, 10, 0));
        foods.add(new Food(1, 10, 0));
        foods.add(new Food(1, 1, 0));
        foods.add(new Food(11, 1, 0));
        
        FoodsController foodsController = new FoodsController(6, foods, maxX, maxY);
        
        Ocean ocean = new Ocean(matr, maxX, maxY,fishes,foodsController, true);
   
        ArrayList<Location> mas = new ArrayList<Location>();
        mas.add(new Location(9, 10));
        mas.add(new Location(10, 12));
        
        Behavior instance = new BehaviorShark(ocean, fishes.get(0));
        
        Location expResult = new Location(9,10);
        boolean e = true;      
        Location result = instance.findNearest(mas);
        boolean r = expResult.equals(result);
        assertEquals(e, r);
    }
    
    @Test
    public void testFindFood(){
        int maxX = 105;
        int maxY = 105;
        ArrayList<DIRECTION> matr = new ArrayList<DIRECTION>();
        for (int j = 0; j < 105; j++){
            if (j%2==1)
                matr.add(DIRECTION.left);
            else
                matr.add(DIRECTION.right);
        }
        ArrayList<Fish> fishes = new ArrayList<Fish>();
                fishes.add(new Fish(2, TYPE.shark, 2, new Location(10,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(8,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(15,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(100,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(1,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(1,1), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(12,12), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,6), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,11), 5, 15, 13, 2, 0));

        ArrayList<Food> foods = new ArrayList<Food>();
        foods.add(new Food(22, 22, 0));
  
        FoodsController foodsController = new FoodsController(1, foods, maxX, maxY);
        
        Ocean ocean = new Ocean(matr, maxX, maxY,fishes,foodsController, true);
        ArrayList<Location> mas = new ArrayList<Location>();
        mas.add(new Location(8, 10));
        mas.add(new Location(10, 6)); 
        mas.add(new Location(10, 11));
        mas.add(new Location(12, 12));
        mas.add(new Location(15, 10));
        
        Behavior instance = new BehaviorFish(ocean, fishes.get(0));

        boolean e = true;      
        ArrayList<Location> result = instance.findFood(fishes.get(0).getSmell(),fishes.get(0).location);
        
        boolean r = true;
        if (mas.size() == result.size())
         for (int i=0; i< mas.size(); i++)
             if (!mas.get(i).equals(result.get(i)))
                r = false;
        assertEquals(e, r);
        
    }
    
    
    
    @Test
    public void testFindFood2(){
        int maxX = 20;
        int maxY = 20;
        ArrayList<DIRECTION> matr = new ArrayList<DIRECTION>();
        for (int j = 0; j < 20; j++){
            if (j%2==1)
                matr.add(DIRECTION.left);
            else
                matr.add(DIRECTION.right);
        }
        ArrayList<Fish> fishes = new ArrayList<Fish>();
        fishes.add(new Fish(2, TYPE.shark, 2, new Location(19,19), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(1,19), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(1,18), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(19,1), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(18,1), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(17,17), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,19), 5, 15, 13, 2, 0));

        ArrayList<Food> foods = new ArrayList<Food>();
        foods.add(new Food(1, 1, 0));
        foods.add(new Food(1, 2, 0));
        
        FoodsController foodsController = new FoodsController(2, foods, maxX, maxY);
        
        Ocean ocean = new Ocean(matr, maxX, maxY,fishes,foodsController, false);
        ArrayList<Location> mas = new ArrayList<Location>();
        mas.add(new Location(17, 17));
        mas.add(new Location(18, 1));
        mas.add(new Location(19, 1));
        mas.add(new Location(1, 18)); 
        mas.add(new Location(1, 19));
        
        Behavior instance = new BehaviorFish(ocean, fishes.get(0));

        boolean e = true;      
        ArrayList<Location> result = instance.findFood(fishes.get(0).getSmell(),fishes.get(0).location);
        
        boolean r = true;
        if (mas.size() == result.size())
         for (int i=0; i< mas.size(); i++)
             if (!mas.get(i).equals(result.get(i)))
                r = false;
        assertEquals(e, r);
        
    }
    
    @Test
    public void testFindFood3(){
        int maxX = 20;
        int maxY = 20;
        ArrayList<DIRECTION> matr = new ArrayList<DIRECTION>();
        for (int j = 0; j < 20; j++){
            if (j%2==1)
                matr.add(DIRECTION.left);
            else
                matr.add(DIRECTION.right);
        }
        ArrayList<Fish> fishes = new ArrayList<Fish>();
        fishes.add(new Fish(2, TYPE.shark, 2, new Location(19,19), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(1,19), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(1,18), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(19,1), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(18,1), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(17,17), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,10), 5, 15, 13, 2, 0));
        fishes.add(new Fish(2, TYPE.fish, 2, new Location(10,19), 5, 15, 13, 2, 0));

        ArrayList<Food> foods = new ArrayList<Food>();
        foods.add(new Food(13, 13, 0));
        
        FoodsController foodsController = new FoodsController(1, foods, maxX, maxY);
        
        Ocean ocean = new Ocean(matr, maxX, maxY,fishes,foodsController, true);
        ArrayList<Location> mas = new ArrayList<Location>();
        mas.add(new Location(17, 17));
 
        Behavior instance = new BehaviorFish(ocean, fishes.get(0));

        boolean e = true;      
        ArrayList<Location> result = instance.findFood(fishes.get(0).getSmell(),fishes.get(0).location);
        
        boolean r = true;
        if (mas.size() == result.size())
         for (int i=0; i< mas.size(); i++)
             if (!mas.get(i).equals(result.get(i)))
                r = false;
        assertEquals(e, r);
        
    }
    
    
    
}
