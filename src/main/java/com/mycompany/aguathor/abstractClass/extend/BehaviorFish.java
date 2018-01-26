/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor.abstractClass.extend;

import com.mycompany.aguathor.enums.BUSY;

import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.abstractClass.Behavior;
import com.mycompany.aguathor.model.Fish;
import java.util.ArrayList;
/**
 *
 * @author Анастасия
 */
public class BehaviorFish extends Behavior{

    /**
     * переменная типа океан
     */
    private Ocean ocean;
    
    /**
     * переменная типа рыба
     */
    private Fish fish;
     
    /**
     * конструктор
     * @param ocean-океан
     * @param fish-рыба
     */
    public BehaviorFish(Ocean ocean, Fish fish){
       // super(ocean,fish, BUSY.food);  
        super(ocean, fish, BUSY.food);
        this.ocean = ocean;
        this.fish = fish;
    }
    
    /**
     * метод, осуществляющие размножение рыбы
     */
    @Override
    public void deliver() {
        //становится мамой
        fish.setIsMom(true);
        //обнуляем число шагов до воспроизведения потомства
        fish.setNumberStepForProgeny(0);
    }
}
