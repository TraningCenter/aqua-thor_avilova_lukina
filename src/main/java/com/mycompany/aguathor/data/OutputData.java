
package com.mycompany.aguathor.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Mariya
 */
/**
 * Класс для выходных данных
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OutputData {
    @XmlElement(name="name")    
    private String name;
    @XmlElement(name="value")
    private int value;
    

    /**
     * Конструктор класса выходных данных
     *
     * @param name название данных
     * @param value значение данных
     *
     */
    public OutputData(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

   
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }  
    

}
