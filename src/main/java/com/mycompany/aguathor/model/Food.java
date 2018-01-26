
package com.mycompany.aguathor.model;


import com.mycompany.aguathor.Location;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author Анастасия
 */
 
 @XmlAccessorType(XmlAccessType.FIELD)
public class Food {
    /**
     * местоположение еды
     */
	 @XmlElement
    public Location location;
    
    /**
     * съедени или нет
     */
    private boolean isEaten;
    
    /**
     * номер
     */
	 @XmlElement
    private int number;
    
    public Food(int x, int y, int number){
        location = new Location(x,y);
        isEaten = false;
        this.number = number;
    }

    public Food() {
        
    }
    
    public boolean getisEaten() {
        return isEaten;
    }

    public void setisEaten (boolean isEaten) {
        this.isEaten = isEaten;
    } 
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    } 
    
     /**
     * изменить местоположение еды
     * @param maxX - максомальная клетка по x
     * @param maxY - максомальная клетка по y
     */
    public void changeLocation(int maxX, int maxY){
        location = new Location((int) (Math.random() * maxX),(int) (Math.random() * maxY));
    }
    
    public void setLocation(Location location){
        this.location = location;
    }
    
    public Location getLocation(){
        return location;
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
        final Food other = (Food) obj;
        
        if (this.isEaten != other.isEaten) {
            return false;
        }
        
        if (!this.location.equals(other.location)) {
            return false;
        }
        
        return this.number == other.number;
    }
}
