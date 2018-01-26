
package com.mycompany.aguathor;

import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.BUSY;

/**
 *
 * @author Анастасия
 */
public class Cell {
    
    /**
     * направление течения в клетке
     */
    private DIRECTION flow;
    
    /**
     * кем занята клетка
     */
    private BUSY whoBusy;
    
    /**
     * номер того, кто занял
     */
    private int number;
    
    /*
    private CSIColor color;
    */
    
    
    public Cell(DIRECTION flow,BUSY whoBusy,int number){
        this.flow = flow;
        this.whoBusy = whoBusy;
        this.number = number;
    }
    
    public DIRECTION getFlow() {
        return flow;
    }

    public void setFlow(DIRECTION flow) {
        this.flow = flow;
    }
    
    public BUSY getWhoBusy() {
        return whoBusy;
    }

    public void setWhoBusy(BUSY whoBusy) {
        this.whoBusy = whoBusy;
    }
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
