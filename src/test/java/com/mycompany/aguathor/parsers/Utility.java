
package com.mycompany.aguathor.parsers;

/**
 *
 * @author Mariya
 */
public class Utility {
    public static String testXMLString = "<ocean><maxX>2</maxX><maxY>2</maxY><isClosed>false</isClosed><flows><flow>right</flow><flow>left</flow></flows>"            
            + "<fishes><fish><progenyPeriod>2</progenyPeriod><type>fish</type><speed>2</speed><location><x>1</x><y>1</y></location><smell>2</smell><lifeTime>5</lifeTime><timeOfStarvation>3</timeOfStarvation><numberOfProgeny>2</numberOfProgeny><number>0</number></fish></fishes>"
            + "<foods><food><location><x>1</x><y>2</y></location><number>0</number></food></foods></ocean>";

    public static String testMonitoring ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><monitoringDOM>\r\n" +
            "<report>\r\n" +
            "<name>CountOfGrassFeedingFishes</name>\r\n" +
            "<value>3</value>\r\n" +
            "<name>SharksCount</name>\r\n" +
            "<value>2</value>\r\n" +
            "</report>\r\n" +
            "</monitoringDOM>\r\n";
    
    public static String testMonitoringJAXB ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<systeminfo>\n" +
            "    "+"<report>\n" +
            "    "+"    "+"<dataList>\n" +
            "    "+"    "+"    "+"<name>CountOfGrassFeedingFishes</name>\n" +
            "    "+"    "+"    "+"<value>3</value>\n" +
            "    "+"    "+"</dataList>\n" +
            "    "+"    "+"<dataList>\n" +
            "    "+"    "+"    "+"<name>SharksCount</name>\n" +
            "    "+"    "+"    "+"<value>2</value>\n" +
            "    "+"    "+"</dataList>\n" +
            "    "+"</report>\n" +
            "</systeminfo>\n";

}
