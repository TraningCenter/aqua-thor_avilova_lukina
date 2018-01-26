
package com.mycompany.aguathor.controller;

import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.abstractClass.extend.BehaviorFish;
import com.mycompany.aguathor.abstractClass.Behavior;
import com.mycompany.aguathor.abstractClass.extend.BehaviorShark;
import com.mycompany.aguathor.enums.TYPE;

/**
 *
 * @author Анастасия
 */
public class FishController {
    /**
     * объект типа рыба
     */
    public Fish fish;
    
    /**
     * поведение рыбы
     */
    public Behavior behavior;
    
    /**
     * океан
     */
    private Ocean ocean;
    
    public FishController(Fish fish, Ocean ocean){
        this.fish = fish;
        this.ocean = ocean;
        if (fish.getType() == TYPE.fish)
            this.behavior = new BehaviorFish(ocean, fish); 
        else
            this.behavior = new BehaviorShark(ocean, fish); 
    }
    
    /**
     * осуществление жизни рыбы или акулы, вызов методов move(), deliver(), die()
    */
    public void live(){
        for (int i = 0; i < fish.getSpeed(); i++){
                
            //пора умирать
            if (fish.getLifeTime() == fish.getNumberStepForLive())
                behavior.die();
                
            else {
                //пора давать потомство
                if ((fish.getProgenyPeriod() == fish.getNumberStepForProgeny()) && (fish.getCanBeMom()))
                    behavior.deliver();
                        
                //иначе плыви или ешь
                else
                    behavior.move();
                }
        }
    }
}
