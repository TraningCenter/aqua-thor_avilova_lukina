/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor.data;
import com.mycompany.aguathor.model.Food;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.TYPE;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mariya
 */
@XmlRootElement(name = "ocean")
@XmlAccessorType(XmlAccessType.FIELD)
public class OceanConfig {    
    @XmlElement(name = "maxY")
    private int maxY;
    @XmlElement(name = "maxX")
    private int maxX;    
    @XmlElement(name = "isClosed")
    private boolean isClosed;    
    @XmlElementWrapper(name = "flows")
    @XmlElement(name = "flow")
    private ArrayList<DIRECTION> flowList;    
    @XmlElementWrapper(name = "fishes")
    @XmlElement(name = "fish")
    private ArrayList<Fish> fishes;
    @XmlElementWrapper(name = "foods")
    @XmlElement(name = "food")
    private ArrayList<Food> foods;
    
    

    public OceanConfig() {
    }

    /**
     * Конструктор класса конфигурации океана
     *
     * @param isTor closed system
     * @param maxX длина поля океана
     * @param maxY ширина поля океана
     * @param isClosed замкнуто/незамкнуто поле океана
     * @param flowList список течений по строкам     
     * @param fishes список рыб
     * @param foods список еды
     */
    public OceanConfig(int maxX, int maxY, boolean isClosed, ArrayList<DIRECTION> flowList, int changeFlow, ArrayList<Fish> fishes, ArrayList<Food> foods) {
        this.maxX = maxX;
        this.maxY = maxY; 
        this.isClosed = isClosed;
        this.flowList = flowList;        
        this.fishes = fishes; 
        this.foods = foods;
    }

    

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public ArrayList<DIRECTION> getFlowList() {
        return flowList;
    }

    public void setFlowList(ArrayList<DIRECTION> flowList) {
        this.flowList = flowList;
    }   

    public ArrayList<Fish> getFishes() {
        return fishes;
    }

    public void setFishes(ArrayList<Fish> fishes) {
        this.fishes = fishes;
    }
    
     public ArrayList<Food> getFood() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
    
    public boolean getIsClosed(){
        return isClosed;
    }
    
    public void setIsClosed(boolean isClosed){
        this.isClosed = isClosed;
    }
    
    public ArrayList<Fish> getSharks() {
        ArrayList<Fish> sharks = new ArrayList<>();
        for(Fish f: fishes){
            if (f.getType() == TYPE.shark)
             sharks.add(f);  
        }
        return sharks;
    }
    
     public ArrayList<Fish> getGrassFeedingFishes() {
        ArrayList<Fish> gfFishes = new ArrayList<>();
        for(Fish f: fishes){
            if (f.getType() == TYPE.fish)
             gfFishes.add(f);  
        }
        return gfFishes;
    }
    
      @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OceanConfig other = (OceanConfig) obj;
        
        if (this.maxX != other.maxX) {
            return false;
        }
        if (this.maxY != other.maxY) {
            return false;
        }
        if (this.isClosed != other.isClosed) {
            return false;
        }        
       
        
        if (this.fishes.size() == other.fishes.size()){
            for (int i = 0; i < this.fishes.size(); i++) {
                if (!this.fishes.get(i).equals(other.fishes.get(i))) 
                       return false;              
            } 
        } else return false;
      
        if (this.foods.size() == other.foods.size()){
            for (int i = 0; i < this.foods.size(); i++) {
                if (!this.foods.get(i).equals(other.foods.get(i))) 
                       return false;                
            } 
        } else return false;
        
        return Objects.equals(this.flowList, other.flowList);
    }
}
