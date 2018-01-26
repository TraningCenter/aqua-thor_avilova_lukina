
package com.mycompany.aguathor;


import com.mycompany.aguathor.data.OceanConfig;
import com.mycompany.aguathor.controller.FoodsController;
import com.mycompany.aguathor.controller.FishController;
import com.mycompany.aguathor.model.Food;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.BUSY;
import com.mycompany.aguathor.enums.TYPE;
import java.util.ArrayList;

/**
 *
 * @author Анастасия
 */
public class Ocean {

   
    
    /**
     * замкнутое или незамкнутое поле
     */
    private boolean isClosed;
    
    /**
     * матрица клеток
     */
    public Cell[][] cellMatr;
    
    /**
     * максимальный размер поля по x
     */
    private int maxX;
    
    /**
     * максимальный размер поля по y
     */
    private int maxY;
    
    /**
     * список рыб
     */
    ArrayList<Fish> fishes;
    
    /**
     * список еды
     */
    FoodsController foodsController;
    
    public static Ocean ocean;
    public OceanConfig oceanConfig;
    
    
    public Ocean(OceanConfig oceanConfig, FoodsController foodsController){
        this.maxX = oceanConfig.getMaxX();
        this.maxY = oceanConfig.getMaxY();
        cellMatr = new Cell[maxX][maxY];
        this.foodsController = foodsController;
        this.fishes = oceanConfig.getFishes();
        createMatr(oceanConfig.getFlowList(), fishes, foodsController.foods);
        this.isClosed = oceanConfig.getIsClosed();
        ocean = this;
        this.oceanConfig = oceanConfig;
    }
    
    public Ocean(ArrayList<DIRECTION> matr, int maxX, int maxY,ArrayList<Fish> fishes,FoodsController foodsController, boolean isClosed){
        this.maxX = maxX;
        this.maxY = maxY;
        cellMatr = new Cell[maxX][maxY];
        this.foodsController = foodsController;
        this.fishes = fishes;
        createMatr(matr,fishes,foodsController.foods);
        this.isClosed = isClosed;
    }
    
    /**
     * заполнение массива клеток течением, рыбами и едой на основании переданных массивов
     */
   public void createMatr(ArrayList<DIRECTION> flowMatr, ArrayList<Fish> fishes,ArrayList<Food> foods){
        for (int i = 0; i < flowMatr.size(); i++) {
            if (flowMatr.get(i) == DIRECTION.left)
                for (int j = 0; j < maxX; j++)
                    cellMatr[i][j] = new Cell(DIRECTION.left,BUSY.none,0);
            else
                for (int j = 0; j < maxX; j++)
                    cellMatr[i][j] = new Cell(DIRECTION.right,BUSY.none,0);
        }
        
        for (int i = 0; i < fishes.size(); i++){
            if (fishes.get(i).getType() == TYPE.fish){
                cellMatr[fishes.get(i).location.y][fishes.get(i).location.x].setWhoBusy(BUSY.fish);
                cellMatr[fishes.get(i).location.y][fishes.get(i).location.x].setNumber(fishes.get(i).getNumber());
            }else
                cellMatr[fishes.get(i).location.y][fishes.get(i).location.x].setWhoBusy(BUSY.shark);   
        }
        for (int i = 0; i < foods.size(); i++){
            cellMatr[foods.get(i).location.y][foods.get(i).location.x].setWhoBusy(BUSY.food);
            cellMatr[foods.get(i).location.y][foods.get(i).location.x].setNumber(foods.get(i).getNumber());
        }
    }
    
    public void updateMatr(ArrayList<FishController> fishes){
        for (int i = 0; i < maxY; i++) {            
            for (int j = 0; j < maxX; j++) {
                    cellMatr[i][j].setWhoBusy(BUSY.none);
            }            
        }
        
        for (int i = 0; i < fishes.size(); i++){
            if (fishes.get(i).fish.getType() == TYPE.fish){
                cellMatr[fishes.get(i).fish.location.y][fishes.get(i).fish.location.x].setWhoBusy(BUSY.fish);
                cellMatr[fishes.get(i).fish.location.y][fishes.get(i).fish.location.x].setNumber(fishes.get(i).fish.getNumber());
            }else
                cellMatr[fishes.get(i).fish.location.y][fishes.get(i).fish.location.x].setWhoBusy(BUSY.shark);   
        }
        for (int i = 0; i < foodsController.foods.size(); i++){
            cellMatr[foodsController.foods.get(i).location.y][foodsController.foods.get(i).location.x].setWhoBusy(BUSY.food);
            cellMatr[foodsController.foods.get(i).location.y][foodsController.foods.get(i).location.x].setNumber(foodsController.foods.get(i).getNumber());
        }
    }

    public void changeLocation(Location oldLocation, Location newLocation,BUSY busy){
        cellMatr[oldLocation.x][oldLocation.y].setWhoBusy(busy);
        cellMatr[oldLocation.x][oldLocation.y].setWhoBusy(busy);
    }
    
    /**
     * 
     * @param location местоположение еды
     * @param type тип еды
     */
    public void eat(Location location, BUSY type){
        int number = cellMatr[location.y][location.x].getNumber();
        
        if (type == BUSY.food){
            for(int i=0; i< foodsController.foods.size();i++)
                if (foodsController.foods.get(i).getNumber() == number)
                   foodsController.foods.get(i).setisEaten(true);
        }
        
        else if (type == BUSY.fish){
            for(int i=0; i< fishes.size();i++)
                if (fishes.get(i).getNumber() == number)
                   fishes.get(i).setIsDied(true);
        }
    }
    /**
     * функция нахождения свободной клетки
     * @return возвращает свободную клетку
     */
    public Location findFreeCell(){
        for (int i = 0; i < maxY; i++)
            for (int j = 0; j < maxX; j++)
                if (cellMatr[i][j].getWhoBusy() == BUSY.none)
                    return new Location(i,j);
        
        return null;
    }   
    
    
    public void refresh(){
        foodsController.refresh();
    }
    
    public boolean getisClosed() {
        return isClosed;
    }

    public void setisClosed(boolean isClosed) {
        this.isClosed = isClosed;
    } 
    
    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    } 
    
    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    } 
    
    public Ocean getOcean(){
        return ocean;
    }    
   
    public ArrayList<Fish> getGrassFeedingFishes(){
        return oceanConfig.getGrassFeedingFishes();
    }
    
    public ArrayList<Fish> getSharks(){
        return oceanConfig.getSharks();
    }  
  
    
}
