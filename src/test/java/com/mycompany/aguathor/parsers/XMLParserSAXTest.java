/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aguathor.parsers;

import com.mycompany.aguathor.SystemInfoWriter;
import com.mycompany.aguathor.data.OceanConfig;
import com.mycompany.aguathor.data.Report;
import com.mycompany.aguathor.data.OutputData;
import com.mycompany.aguathor.model.Food;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.*;

import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.TYPE;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Mariya
 */
public class XMLParserSAXTest {
    
    public XMLParserSAXTest() {
    }
    
    

    /**
     * Test of read method, of class XMLParserSAX.
     */
    @Test
    public void testRead() {
        String testXMLString = Utility.testXMLString;
        InputStream config = new ByteArrayInputStream(testXMLString.getBytes());
        XMLParserSAX parser = new XMLParserSAX();
        OceanConfig oceanConfigResult = parser.read(config);
        OceanConfig oceanConfig = new OceanConfig();
        oceanConfig.setMaxX(2);
        oceanConfig.setMaxY(2);
        oceanConfig.setIsClosed(false);
        ArrayList<Fish> fishes = new ArrayList<>();
        ArrayList<Food> foods = new ArrayList<>();
        Location fishLocation = new Location();
        fishLocation.x = 1;
        fishLocation.y = 1;
        Fish fish = new Fish(2, TYPE.fish, 2, fishLocation, 2, 5, 3, 2, 0);
        fishes.add(fish);             
        Food food = new Food(1, 2, 0);
        foods.add(food);
        ArrayList<DIRECTION> flows = new ArrayList<>();
        DIRECTION flow1 = DIRECTION.right;
        DIRECTION flow2 = DIRECTION.left;
        flows.add(flow1);
        flows.add(flow2);
        oceanConfig.setFishes(fishes);
        oceanConfig.setFoods(foods);
        oceanConfig.setFlowList(flows);        
        assertTrue(oceanConfig.equals(oceanConfigResult));      
    }

  
    /**
     * Test of write method, of class XMLParserSAX.
     */
    @Test
    public void testWrite() {
        ArrayList<OutputData> dataList= new ArrayList();
        dataList.add(new OutputData("CountOfGrassFeedingFishes", 3));
        dataList.add(new OutputData("SharksCount", 2));
        ArrayList<Report> reports = new ArrayList();
        reports.add(new Report (dataList));
        SystemInfoWriter systemInfoWriter = new SystemInfoWriter(reports);
        try {
            String testMonitoring = Utility.testMonitoring;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            XMLParserDOM parser = new XMLParserDOM();
            parser.write(systemInfoWriter, outputStream);
            String result = new String(outputStream.toByteArray(), "UTF-8");            
            assertEquals(testMonitoring, result);
        } catch(Exception ex){
        }
    }
    
}
