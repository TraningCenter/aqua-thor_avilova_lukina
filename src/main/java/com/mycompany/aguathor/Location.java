
package com.mycompany.aguathor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;



/**
 * Класс местоположение объекта, имеет два поля: координата x и y
 * @author Анастасия
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Location {
    @XmlElement
    public int x;
    @XmlElement
    public int y;
    
    public Location(){
        this.x = 0;
        this.y = 0;
    } 
    
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }   
    
    public boolean equals(Location  obj){
        return ((this.x == obj.x) && (this.y == obj.y));
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        return hash;
    }
}
