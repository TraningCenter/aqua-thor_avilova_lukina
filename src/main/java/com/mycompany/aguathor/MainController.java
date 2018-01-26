/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor;
import com.mycompany.aguathor.controller.FishController;
import com.mycompany.aguathor.enums.BUSY;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.ui.PrintOcean;
import java.util.ArrayList;
/**
 *
 * @author Анастасия
 */
public class MainController {
    
    /**
     * список всех рыб
     */
    private ArrayList<FishController> fishes;
    
    /**
     * переменная типа океан
     */
    private Ocean ocean;
    
    /**
     * максимальный размер поля по x
     */
    private int maxX;
    
    /**
     * максимальный размер поля по y
     */
    private int maxY;
    
    /**
     * переменная для открисовки
     */
    private PrintOcean printOcean;
   
    
    public MainController(ArrayList<FishController> fishes, int MaxX, int MaxY, PrintOcean printOcean){
        this.fishes = fishes;
        this.ocean = Ocean.ocean;
        this.maxX = MaxX;
        this.maxY = MaxY;
        this.printOcean = printOcean;
    }
    
    /**
     * обновление списка рыб, добавление новых и удаление умерших
    */
   private int update(int n){
       int k = 0;
       for (int i = 0; i < fishes.size(); i++) {
            if (fishes.get(i).fish.getIsMom()){
                for (int j=0; j < fishes.get(i).fish.getNumberOfProgeny(); j++){

                    Location location = findFreeLocationForChild(fishes.get(i).fish);
                
                    Fish fish = new Fish(fishes.get(i).fish.getProgenyPeriod(),
                                        fishes.get(i).fish.getType(),
                                        fishes.get(i).fish.getSpeed(), 
                                        location,fishes.get(i).fish.getSmell(),
                                        fishes.get(i).fish.getLifeTime(),
                                        fishes.get(i).fish.getTimeOfStarvation(),
                                        fishes.get(i).fish.getNumberOfProgeny(),
                                        fishes.size());
                    
                    fishes.add(new FishController(fish, ocean));


                    ocean.fishes.add(fish);
                    ocean.cellMatr[fish.location.y][fish.location.x].setWhoBusy(BUSY.fish);
                }
                fishes.get(i).fish.setIsMom(false);
            }
            
            if (fishes.get(i).fish.getIsDied()) {
                fishes.remove(i);
                ocean.fishes.remove(i);
                if (i<n)
                    k++;
            }
       }
       
        ocean.refresh();
        ocean.updateMatr(fishes);
        return k;
    }
    
    /**
     *  метод идет по всему списку fishes и у каждого объекта вызывает метод live
    */
    public void live(boolean stop, SystemInfoWriter systemInfoWriter) throws InterruptedException{
        int k;
        printOcean.print();
        while (!stop){ 
            for (int i = 0; i < fishes.size(); i++) {
               
                fishes.get(i).live();
                k = update(i);  
                i -= k;
                printOcean.print();                
            }   
            systemInfoWriter.writeData();
            //printOcean.print();
        }
    }
    
    
    /**
     * поиск свободной клетки для потомства рыбы
     * @param fish чье потомство
     * @return местоположение
     */
    public Location findFreeLocationForChild(Fish fish){
        int i=1;
        while((i != maxX/2) || (i != maxY/2)){
            if ((fish.location.x-i>=0)&& (fish.location.x-i<this.maxX)&& (fish.location.y-i>=0)&& (fish.location.y-i<this.maxY)&&(ocean.cellMatr[fish.location.y-i][fish.location.x-i].getWhoBusy() == BUSY.none))
                return new Location(fish.location.x-i, fish.location.y-i);
            else if ((fish.location.x>=0)&& (fish.location.x<this.maxX)&& (fish.location.y-i>=0)&& (fish.location.y-i<this.maxY)&&(ocean.cellMatr[fish.location.y-i][fish.location.x].getWhoBusy() == BUSY.none))
                return new Location (fish.location.x, fish.location.y-i);
            else if ((fish.location.x+i>=0)&& (fish.location.x+i<this.maxX)&& (fish.location.y-i>=0)&& (fish.location.y-i<this.maxY)&&(ocean.cellMatr[fish.location.y-i][fish.location.x+i].getWhoBusy() == BUSY.none))
                return new Location (fish.location.x+i, fish.location.y-i);
            else if ((fish.location.x-i>=0)&& (fish.location.x-i<this.maxX)&& (fish.location.y>=0)&& (fish.location.y<this.maxY)&&(ocean.cellMatr[fish.location.y][fish.location.x-i].getWhoBusy() == BUSY.none))
                return new Location (fish.location.x-i, fish.location.y);
            else if ((fish.location.x+i>=0)&& (fish.location.x+i<this.maxX)&& (fish.location.y>=0)&& (fish.location.y<this.maxY)&&(ocean.cellMatr[fish.location.y][fish.location.x+i].getWhoBusy() == BUSY.none))
                return new Location (fish.location.x+i, fish.location.y);
            else if ((fish.location.x-i>=0)&& (fish.location.x-i<this.maxX)&& (fish.location.y+i>=0)&& (fish.location.y+i<this.maxY)&&(ocean.cellMatr[fish.location.y+i][fish.location.x-i].getWhoBusy() == BUSY.none))
                return new Location (fish.location.x-i, fish.location.y+i);
            else if ((fish.location.x+i>=0)&& (fish.location.x+i<this.maxX)&& (fish.location.y+i>=0)&& (fish.location.y+i<this.maxY)&&(ocean.cellMatr[fish.location.y+i][fish.location.x+i].getWhoBusy() == BUSY.none))
                return new Location (fish.location.x+i, fish.location.y+i);
            
            i++;
        }
        
        return ocean.findFreeCell();    
    }
    
    
}
