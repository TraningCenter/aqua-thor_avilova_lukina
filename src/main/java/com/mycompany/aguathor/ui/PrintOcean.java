/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor.ui;


import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.mycompany.aguathor.Cell;
import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.enums.BUSY;
import com.mycompany.aguathor.enums.DIRECTION;
import java.io.Console;
import java.io.IOException;
/**
 *
 * @author Анастасия
 */
public class PrintOcean {
      
    /**
     * консоль 
     */
    private Screen screen;
    
    /**
     * размеры
     */
    private int maxX;
    
    /**
     * размеры
     */
    private int maxY;
    
    /**
     * океан
     */
    private Ocean ocean;
   
    public PrintOcean(Screen screen, int maxX,int maxY, Ocean ocean){
        this.screen = screen;
        this.maxX = maxX;
        this.maxY = maxY;
        this.ocean = ocean;
    }

    public PrintOcean() {
        
    }
    
    /**
     * начать отображение
     
     */
    public void startScreen() throws IOException {
        screen.startScreen();
        screen.setCursorPosition(null);
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    public void setScreen(Screen screen){
        this.screen = screen;
    }

    /**
     * транспонировать матрицу
     * @return возращает транспониованную матрицу
     */
    private Cell[][] transpose() {
        Cell[][] tCells = new Cell[maxX][maxY];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                tCells[j][i] = ocean.cellMatr[i][j];
            }
        }
        return tCells;
    }
    
    /**
     * печать матрицы
     * @throws InterruptedException 
     */
    public void print() throws InterruptedException{   
        Cell[][] cells = transpose();
        for (int i = 0; i < maxY; i++)
            for (int j = 0; j < maxX; j++){
                if (cells[i][j].getWhoBusy() == BUSY.none ){
                    if (cells[i][j].getFlow() == DIRECTION.left )
                        screen.putString(i,j,"<", Terminal.Color.BLUE,Terminal.Color.BLACK);
                    else
                        screen.putString(i,j,">", Terminal.Color.BLUE,Terminal.Color.BLACK);
                }else if (cells[i][j].getWhoBusy() == BUSY.fish)
                    screen.putString(i,j,"x", Terminal.Color.GREEN,Terminal.Color.BLACK);
                else if (cells[i][j].getWhoBusy() == BUSY.shark)
                    screen.putString(i,j,"x", Terminal.Color.RED,Terminal.Color.BLACK);
                else
                    screen.putString(i,j,"o", Terminal.Color.YELLOW,Terminal.Color.BLACK);                     
            }
        screen.refresh(); 
        Thread.sleep(1000);
    }

  public void refresh() throws IOException {
        screen.refresh();
    }

   

}
