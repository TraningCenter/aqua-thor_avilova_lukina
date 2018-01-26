
package com.mycompany.aguathor.parsers;

import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.model.Food;
import com.mycompany.aguathor.Location;
import com.mycompany.aguathor.data.OceanConfig;
import com.mycompany.aguathor.SystemInfoWriter;
import com.mycompany.aguathor.enums.DIRECTION;
import com.mycompany.aguathor.enums.TYPE;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Mariya
 */
/**
 * Класс для SAX-парсинга
 */
public class XMLParserSAX extends DefaultHandler implements IParser {
   
    private OceanConfig oceanConfig = new OceanConfig();
    
    private ArrayList<DIRECTION> flows;
    private ArrayList<Fish> fishes;
    private ArrayList<Food> foods;
    boolean isFood = false;
    private Fish fish;
    private Food food;
    private Location location;
    private String currentElement;
    private TransformerFactory factory;
    private SAXTransformerFactory saxTransformerFactory;
    private TransformerHandler transformer;
    
     /**
     * read чтение XML-файла
     *
     * @param config input stream
     * @return oceanConfig
     */
    @Override
    public OceanConfig read(InputStream config) {
        try {            
           
            SAXParserFactory factory = SAXParserFactory.newInstance(); 
            SAXParser parser = factory.newSAXParser(); 
            XMLParserSAX saxParser = new XMLParserSAX();  
            parser.parse(config, saxParser); 
            OceanConfig oceanConfig = saxParser.getOceanConfig();
            
            return oceanConfig;
        } catch (Exception e) {
            return null;
        }
    }
    
    OceanConfig getOceanConfig() {
        return oceanConfig;
    }
    
    /**
     * startElement вызывается в начале XML-элемента
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        switch (currentElement) {
            case "ocean":
                oceanConfig = new OceanConfig();
                break;
            case "flows":
                flows = new ArrayList<>();
                break;
            case "fish":
                fish = new Fish();
                break;
            case "food":
                food = new Food();
                break;
            case "foods":
                foods = new ArrayList<>();
                isFood = true;
                break;    
            case "fishes":
                fishes = new ArrayList<>();
                break;
            case "location":
                location = new Location();
                break;
        }
    }
    
     /**
     * characters читает информацию между тегами
     *
     * @param ch символы
     * @param start начальная позиция
     * @param length длина
     
     */      
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length);
        if (str.contains("<") || currentElement == null) {
            return;
        }
        switch (currentElement) {            
            case "maxY":
                oceanConfig.setMaxY(Integer.valueOf(str));
                break;
            case "maxX":
                oceanConfig.setMaxX(Integer.valueOf(str));
                break;
            case "isClosed":
                oceanConfig.setIsClosed(Boolean.valueOf(str));
                break;
            case "flow":
                flows.add(DIRECTION.valueOf(str));
                break;    
            case "progenyPeriod":
                fish.setProgenyPeriod(Integer.valueOf(str));
                break;
                
            case "lifeTime":
                fish.setLifeTime(Integer.valueOf(str));
                break;
           
            case "type":
               switch (str) {
                    case "fish":
                        fish.setType(TYPE.fish);
                        break;
                    case "shark":
                        fish.setType(TYPE.shark);
                        break;   
                        default:
                }
                break;
            case "speed":
                fish.setSpeed(Integer.valueOf(str));
                break;
            case "x":
                location.x = Integer.valueOf(str);
                break;
            case "y":
                location.y = Integer.valueOf(str);
                break;
            case "smell":
                fish.setSmell(Integer.valueOf(str));
                break;
            case "timeOfStarvation":
                fish.setTimeOfStarvation(Integer.valueOf(str));
                break;
            case "numberOfProgeny":
                fish.setNumberOfProgeny(Integer.valueOf(str));
                break;
            case "number":
                fish.setNumber(Integer.valueOf(str));
                break;
        }
    }
    
     /**
     * endElement вызывается в конце XML-элемента
     *
     * @param uri
     * @param localName
     * @param qName
  
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "flows":
                oceanConfig.setFlowList(flows);
                break;
            case "location":
                if (!isFood){
                    fish.setLocation(location);
                } else {
                    food.setLocation(location); 
                }
                break;
            case "fish":                
                fishes.add(fish);
                break;  
            case "food":
                foods.add(food);
            case "fishes":
                oceanConfig.setFishes(fishes);
                break; 
            case "foods":
                oceanConfig.setFoods(foods);
                break; 
        }
        currentElement = null;
    }

    /**
     * запись информации о системе 
     * 
     * @param systemInfoWriter SystemInfoWriter
     * @param ocConfig OceanConfig
     * @param outputStream OutputStream
     */
    @Override
    public void write(SystemInfoWriter systemInfoWriter, OutputStream outputStream) {
        try {
            factory = TransformerFactory.newInstance().newInstance();
            saxTransformerFactory = (SAXTransformerFactory) factory;
            transformer = saxTransformerFactory.newTransformerHandler();           
            transformer.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setResult(new StreamResult(outputStream));
            writeRoot(systemInfoWriter);
        } catch (TransformerConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * создание корневого элемента документа
     *
     * @param systemInfoWriter SystemInfoWriter
     *
     */
    private void writeRoot(SystemInfoWriter systemInfoWriter) throws SAXException {
        transformer.startDocument();
        transformer.startElement("", "", "monitoringSAX", null);
        writeReport(systemInfoWriter);
        transformer.endElement("", "", "monitoringSAX");
        transformer.endDocument();
    }
     
     /**
     *создание элементов документа и запись информации
     *
     * @param metricsWriter MetricsWriter
     *
     */
    private void writeReport(SystemInfoWriter systemInfoWriter) throws SAXException {
        for (int i = 0; i < systemInfoWriter.reports.size(); i++) {
            transformer.startElement("", "", "report", null);             
            for (int j = 0; j < systemInfoWriter.reports.get(i).getDataList().size(); j++) {                
                transformer.startElement("", "", "name", null);
                char[] tempName = String.valueOf(systemInfoWriter.reports.get(i).getDataList().get(j).getName()).toCharArray();
                transformer.characters(tempName, 0, tempName.length);
                transformer.endElement("", "", "name");
                transformer.startElement("", "", "value", null);
                char[] tempValue = String.valueOf(systemInfoWriter.reports.get(i).getDataList().get(j).getValue()).toCharArray();
                transformer.characters(tempValue, 0, tempValue.length);
                transformer.endElement("", "", "value");                
            }           
            transformer.endElement("", "", "report");
        }
    }
}
