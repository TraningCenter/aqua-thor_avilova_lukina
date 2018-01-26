/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor.abstractClass;

import com.mycompany.aguathor.enums.BUSY;
import com.mycompany.aguathor.enums.BUSY;
import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.DIRECTION;

import com.mycompany.aguathor.Location;
import com.mycompany.aguathor.Location;
import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.model.Fish;
import java.util.ArrayList;

/**
 * 
 * @author Анастасия
 */
public abstract class  Behavior {
    
    /*переменная типа океан*/
    private Ocean ocean;
    
    /*переменная типа рыба*/
    private Fish fish;
    
    /*кого есть*/
    private BUSY whatEat;
    
     /**
     * конструктор
     * @param ocean-океан
     * @param fish-рыба
     * @param whateat-кого ест
     */
    public Behavior(Ocean ocean, Fish fish, BUSY whatEat) {
        this.ocean = ocean;
        this.fish = fish;
        this.whatEat = whatEat;
    }
    
    /**
     * метод, осуществляющие движение рыбы
     * рыба делает ровно 1 шаг, либо передвигается на одну клетку, либо ест
     */  
    public void move() {

        int step = 0;
        
        ArrayList<Location> masFood = findFood(fish.getSmell(), fish.location );
        
        //если нет еды рядом, то плыть по течению
        if (masFood.size()==0 ){
            
            //если течение справа
            if (ocean.cellMatr[fish.location.x][fish.location.y].getFlow() == DIRECTION.right){
                //если не у края, то плывем
                if ((fish.location.y < ocean.getMaxY()-1) &&(ocean.cellMatr[fish.location.y+1][fish.location.x].getWhoBusy() == BUSY.none))
                    fish.location = new Location(fish.location.x,++fish.location.y);
                //если у края
                else{
                    //не замкнуто-идем в другой конец
                    if ((!ocean.getisClosed()) && (ocean.cellMatr[0][fish.location.x].getWhoBusy() == BUSY.none))
                        fish.location = new Location(fish.location.x,0);
                    //замкнуто- поднимаемся или опускаемся РАНДОМНО
                    else{
                        //рандом
                        int rand = (int) (Math.random() * 3);
                        
                        if ((rand  % 2) > 1){
                            if ((fish.location.x < ocean.getMaxX()-1) && (ocean.cellMatr[fish.location.y][fish.location.x+1].getWhoBusy() == BUSY.none))
                                fish.location = new Location(++fish.location.x,fish.location.y);
                            else if (ocean.cellMatr[fish.location.y][fish.location.x-1].getWhoBusy() == BUSY.none)
                                fish.location = new Location(--fish.location.x,fish.location.y);
                        }
                        else{
                            if ((fish.location.x > 0) && (ocean.cellMatr[fish.location.y][fish.location.x-1].getWhoBusy() == BUSY.none))
                                fish.location = new Location(--fish.location.x,fish.location.y);
                            else if (ocean.cellMatr[fish.location.y][fish.location.x+1].getWhoBusy() == BUSY.none)
                                fish.location = new Location(++fish.location.x,fish.location.y);
                        }
                    }      
                }
            }
            
            //если течение слева
            else{
                //если не у края, то плывем
                if ((fish.location.y > 0) && ((ocean.cellMatr[fish.location.y-1][fish.location.x].getWhoBusy() == BUSY.none)))
                    fish.location = new Location(fish.location.x,--fish.location.y);
                else{
                    //не замкнуто-идем в другой конец
                    if ((!ocean.getisClosed()) && (ocean.cellMatr[ocean.getMaxY()-1][fish.location.x].getWhoBusy() == BUSY.none))
                        fish.location = new Location(fish.location.x,ocean.getMaxY()-1);
                    //замкнуто- поднимаемся или опускаемся РАНДОМНО
                    else{
                        //рандом
                        int rand = (int) (Math.random() * 3);
                        
                        if ((rand  % 2) > 1){
                            if ((fish.location.x<ocean.getMaxX()-1) && (ocean.cellMatr[fish.location.y][fish.location.x+1].getWhoBusy() == BUSY.none))
                                fish.location = new Location(++fish.location.x,fish.location.y);
                            else if (ocean.cellMatr[fish.location.y][fish.location.x-1].getWhoBusy() == BUSY.none)
                                fish.location = new Location(--fish.location.x,fish.location.y);
                        }
                        else{
                            if ((fish.location.x > 0) && (ocean.cellMatr[fish.location.y][fish.location.x-1].getWhoBusy() == BUSY.none))
                                fish.location = new Location(--fish.location.x,fish.location.y);
                            else if (ocean.cellMatr[fish.location.y][fish.location.x+1].getWhoBusy() == BUSY.none)
                                fish.location = new Location(++fish.location.x,fish.location.y);
                        }
                    }    
                        
                }    
            }
            
            step = fish.getNumberStepForLive() + 1;
            fish.setNumberStepForLive(step);
            step = fish.getNumberStepForProgeny() +1;
            fish.setNumberStepForProgeny(step);
        }
        
        //если рядом есть еда
        else{
            Location food = findNearest(masFood);
            
            //в соседней клетке, она ест и передвигается на ее клетку
            if ((((fish.location.x == food.x) && (Math.abs(fish.location.y - food.y)==1))
                 ||((fish.location.y == food.y) && (Math.abs(fish.location.x - food.x)==1))) 
                    || ((Math.abs(fish.location.x - food.x)==1) && (Math.abs(fish.location.y - food.y)==1))){
                //кушает
                ocean.eat(food, whatEat);
                //занимает ее клетку
                fish.location.x = food.x;
                fish.location.y = food.y;
                
                //обнуляем кол-во шагов до смерти
                fish.setNumberStepForLive(0);
                
                //увеличиваем число шагов до воспроизведения потомства
                step = fish.getNumberStepForProgeny()+1;
                fish.setNumberStepForProgeny(step);
            }    
            
            //если не в соседней, то плывет за едой, может ходить по диагонали!
            else{
                
                //еда справа
                if (fish.location.y < food.y){
                    
                    //если есть место, плывем
                    if ((fish.location.y < ocean.getMaxY()-1) && (ocean.cellMatr[fish.location.y+1][fish.location.x].getWhoBusy() == BUSY.none))
                        fish.location = new Location(fish.location.x,++fish.location.y);
                    
                    //не замкнуто-идем в другой конец
                    else if ((!ocean.getisClosed()) && (ocean.cellMatr[0][fish.location.x].getWhoBusy() == BUSY.none))
                        fish.location = new Location(fish.location.x,0);
                    
                }
                
                //еда слева, если координаты по x равны, то никуда не плывет
                else if (fish.location.y > food.y){
                   //если есть место, плывем
                    if ((fish.location.y > 0) && (ocean.cellMatr[fish.location.y-1][fish.location.x].getWhoBusy() == BUSY.none))
                        fish.location = new Location(fish.location.x,--fish.location.y);
                    //не замкнуто-идем в другой конец
                    else if ((!ocean.getisClosed()) && (ocean.cellMatr[ocean.getMaxY()-1][fish.location.x].getWhoBusy() == BUSY.none))
                        fish.location = new Location(fish.location.x,ocean.getMaxY()-1);
                }
                
                //еда внизу
                if (fish.location.x < food.x){
                    //если есть место, плывем
                    if ((fish.location.x<ocean.getMaxX()-1) && (ocean.cellMatr[fish.location.y][fish.location.x+1].getWhoBusy() == BUSY.none))
                        fish.location = new Location(++fish.location.x,fish.location.y);
                    
                    //не замкнуто-идем в другой конец
                    else if ((!ocean.getisClosed()) && (ocean.cellMatr[fish.location.y][0].getWhoBusy() == BUSY.none))
                        fish.location = new Location(0,fish.location.y);
                   
                }
                //еда вверху
                else if (fish.location.x > food.x){
                    //если есть место, плывем
                    if ((fish.location.y>0) && (ocean.cellMatr[fish.location.y][fish.location.x-1].getWhoBusy() == BUSY.none))
                        fish.location = new Location(--fish.location.x,fish.location.y);
                    //не замкнуто-идем в другой конец
                    else if ((!ocean.getisClosed()) && (ocean.cellMatr[fish.location.y][ocean.getMaxX()-1].getWhoBusy() == BUSY.none))
                        fish.location = new Location(ocean.getMaxX()-1,fish.location.y);
                    
                }
                step = fish.getNumberStepForLive() + 1;
                fish.setNumberStepForLive(step);
                step = fish.getNumberStepForProgeny()+1;
                fish.setNumberStepForProgeny(step);
            }
                   
        }
    };
       
    /**
     * метод, осуществляющие размножение рыбы
     */
    public abstract void deliver();
    
    /**
     * метод, осуществляющие смерть рыбы
     */
    public void die() {
        fish.setIsDied(true);
    }
 
    
    /**
     * ф-ция нахождения еды, если ничего нет, то возвращает текущую позицию
     * @param radius-нюх рыбы
     * @param location-местоположение рыбы
     * @return возвращает список еды, которую может съесть рыба и которая 
     * находится от местоположения рыбы на расстоянии, меньшем чем radius
     */
    public ArrayList<Location> findFood(int radius, Location location){
        
        ArrayList<Location> mas = new ArrayList<Location>();
        int ni = 0 , nj = 0;
        for (int i = location.x-radius; i <= location.x+radius ; i++)
            for (int j = location.y-radius; j <= location.y+radius; j++){
                
                //если не замкнуто
                if (!ocean.getisClosed()){
                    if (i > ocean.getMaxX()-1 )
                        ni = i - ocean.getMaxX();
                    else if (i < 0)
                        ni = ocean.getMaxX() + i;
                    else
                        ni = i;
                
                    if (j > ocean.getMaxY()-1 )
                        nj = j - ocean.getMaxY();
                    else if (j < 0)
                        nj = ocean.getMaxY() + j;
                    else
                        nj = j;

                }
                
                //если замкнуто
                else{
                    if (i > ocean.getMaxX()-1 )
                        ni = ocean.getMaxX()-1;
                    else if (i < 0)
                        ni = 0;
                    else
                        ni = i;
                
                    if (j > ocean.getMaxY()-1 )
                        nj =  ocean.getMaxY()-1;
                    else if (j < 0)
                        nj = 0;
                    else
                        nj = j;
                }
                
                if(ocean.cellMatr[nj][ni].getWhoBusy() == whatEat)
                       mas.add(new Location(ni,nj));
            } 
        return mas;
    }

    /**
     * ф-ция нахождения самой близкой еды
     * @param mas-список еды, близкой к рыбе
     * @return возвращает тот элемент списка mas, который находится к рыбе 
     * ближе всех
     */
    public Location findNearest(ArrayList<Location> mas){
        double min = ocean.getMaxX();
        Location location = new Location();
        for (int i=0; i<mas.size();i++){
            double s = Math.sqrt(Math.pow((mas.get(i).x - fish.location.x),2)+
                    Math.pow((mas.get(i).y - fish.location.y),2));
            if (min > s){
                location.x = mas.get(i).x;
                location.y = mas.get(i).y;
                min = s;
            }
        }
        
        return location;
    }
}
