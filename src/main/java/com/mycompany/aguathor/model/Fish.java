
package com.mycompany.aguathor.model;

import com.mycompany.aguathor.Location;
import com.mycompany.aguathor.enums.TYPE;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Анастасия
 */
 @XmlAccessorType(XmlAccessType.FIELD)
public class Fish {
    
    /**
     * период потомтва, задается в числе шагов
     */
	  @XmlElement
    private int progenyPeriod;
    
    /**
     * тип рыбы
    * true-обычная false-акула
    */
	 @XmlElement
    private TYPE type; 
    
    /**
     * скорость рыбы
     */
	  @XmlElement
    private int speed;
    
    /**
     * местоположение еды
     */
	  @XmlElement
    public Location location;
    
    /**
     * чуйка, задается в кол-ве клеток
     */
	  @XmlElement
    private int smell;
    
    /**
     * время жизни, задается в числе шагов
     */
	  @XmlElement
    private int lifeTime;
    
    /**
     * время голодной смерти, задается в числе шагов
     */
	  @XmlElement
    private int timeOfStarvation;
    
    /**
     * кол-во оставляемого после себя потомства
     */
	  @XmlElement
    private int numberOfProgeny;
       
    /**
     * номер рыбы
     */
	  @XmlElement
    private int number;
    
    /**
     * дала потомство или нет
     */
    private boolean isMom;
    
    /**
     * умерла или нет
     */
    private boolean isDied;

    /**
     * количество пройденных шагов для жизни
     */
    private int numberStepForLive;
    
    /**
     * количество пройденных шагов для потомства
     */
    private int numberStepForProgeny ;

    /**
     * может ли рыба давать потомство
     */
    private boolean canBeMom;

 
    public Fish(int progenyPeriod, TYPE type, int speed, Location location, int smell, int lifeTime, int timeOfStarvation, int numberOfProgeny, int number){
        this.progenyPeriod = progenyPeriod;
        this.type = type;
        this.speed = speed;
        this.location = location;
        this.smell = smell;
        this.lifeTime = lifeTime;
        this.timeOfStarvation = timeOfStarvation;
        this.numberOfProgeny = numberOfProgeny;
        this.number = number;
        this.isMom = false;
        this.isDied = false;
        this.numberStepForLive = 0;
        this.numberStepForProgeny = 0;
        this.canBeMom = true;
    }

    public Fish() {
        this.isMom = false;
        this.isDied = false;
        this.numberStepForLive = 0;
        this.numberStepForProgeny = 0;
        this.canBeMom = true;
    }
    
    
    public int getProgenyPeriod() {
        return progenyPeriod;
    }

    public void setProgenyPeriod(int progenyPeriod) {
        this.progenyPeriod = progenyPeriod;
    }

    public TYPE getType() {
        return type;
    }

    public void setType (TYPE  type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSmell() {
        return smell;
    }

    public void setSmell(int smell) {
        this.smell = smell;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getTimeOfStarvation() {
        return timeOfStarvation;
    }

    public void setTimeOfStarvation(int timeOfStarvation) {
        this.timeOfStarvation = timeOfStarvation;
    }

    public int getNumberOfProgeny() {
        return numberOfProgeny;
    }

    public void setNumberOfProgeny(int numberOfProgeny) {
        this.numberOfProgeny = numberOfProgeny;
    }
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean getIsMom() {
        return isMom;
    }

    public void setIsMom(boolean  isMom) {
        this.isMom = isMom;
    }
    
    public boolean getIsDied() {
        return isDied;
    }

    public void setIsDied(boolean  isDied) {
        this.isDied = isDied;
    }
    
    
    public int getNumberStepForLive() {
        return numberStepForLive;
    }

    public void setNumberStepForLive(int number) {
        this.numberStepForLive = number;
    }
    
    public int getNumberStepForProgeny() {
        return numberStepForProgeny;
    }

    public void setNumberStepForProgeny(int number) {
        this.numberStepForProgeny = number;
    }
    
    public boolean getCanBeMom() {
        return canBeMom;
    }

    public void setCanBeMom(boolean  can) {
        this.canBeMom = can;
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
        final Fish other = (Fish) obj;
        
        if (this.progenyPeriod != other.progenyPeriod) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.speed != other.speed) {
            return false;
        }
        if (!this.location.equals(other.location)) {
            return false;
        }
        if (this.smell != other.smell) {
            return false;
        }
        if (this.lifeTime != other.lifeTime) {
            return false;
        }
        if (this.timeOfStarvation != other.timeOfStarvation) {
            return false;
        }
        if (this.numberOfProgeny != other.numberOfProgeny) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        
        if (this.isMom != other.isMom) {
            return false;
        }
        if (this.isDied != other.isDied) {
            return false;
        }
        if (this.numberStepForLive != other.numberStepForLive) {
            return false;
        }
        if (this.numberStepForProgeny != other.getNumberStepForProgeny()) {
            return false;
        }
        return this.canBeMom == other.canBeMom;
    }
}
