
package com.mycompany.aguathor.parsers;
import com.mycompany.aguathor.data.OceanConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.model.Food;
import com.mycompany.aguathor.enums.TYPE;
import com.mycompany.aguathor.Location;
import com.mycompany.aguathor.SystemInfoWriter;
import com.mycompany.aguathor.enums.DIRECTION;
import java.io.IOException;

import java.util.ArrayList;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author Mariya
 */
/**
 * Класс для DOM-парсинга
 */
public class XMLParserDOM implements IParser{
    private OceanConfig oceanConfig;

     /**
     * read чтение XML-файла
     *
     * @param config input stream
     * @return oceanConfig
     */
    @Override
    public OceanConfig read(InputStream config) {
        try {
            Document document = createDocumentBuilder().parse(config);
            parseXML(document);
            return oceanConfig;
        } catch (IOException | NullPointerException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * создание documentBuilder
     *
     * @return documentBuilder
     * @throws ParserConfigurationException
     */
    private DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
        return docBuildFactory.newDocumentBuilder();
    }
    
    /**
     * парсинг oceanСonfig
     *
     * @param document
     * @throws NullPointerException
     */
    private void parseXML(Document document) throws NullPointerException {
        oceanConfig = new OceanConfig();
        Element documentElement = document.getDocumentElement();
        
        parseOceanMaxY(getFirstElementByTagName(documentElement, "maxY"));
        parseOceanMaxX(getFirstElementByTagName(documentElement, "maxX"));
        parseIsClosed(getFirstElementByTagName(documentElement, "isClosed"));
        parseOceanFlow(getFirstElementByTagName(documentElement, "flows"));        
        parseFish(getFirstElementByTagName(documentElement, "fishes"));
        parseFood(getFirstElementByTagName(documentElement, "foods"));
        
    }
    
     /**
     * получение первого элемента по тегу
     *
     * @param element элемент для поиска
     * @param tagName тег
     * @return элемент
     */
    private Element getFirstElementByTagName(Element element, String tagName) {
        return (Element) element.getElementsByTagName(tagName).item(0);
    }
    
    /**
     * парсинг списка рыб
     *
     * @param someFish рыба
     * @throws NullPointerException
     */
    private void parseFish(Element someFish) throws NullPointerException {
        
        ArrayList<Fish> fishes = new ArrayList<>();
        
        String name = null;
        if (someFish.getTagName().equals("fishes")) {
            name = "fish";
        }
        NodeList fishNodeList = someFish.getElementsByTagName(name);
        for (int i = 0; i < fishNodeList.getLength(); i++) {
            if (fishNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element fishElement = (Element) fishNodeList.item(i);
                Fish fish = new Fish();
                NodeList childNodes = fishElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNodes.item(j);
                        switch (childElement.getNodeName()) {
                           
                            case "progenyPeriod":
                                fish.setProgenyPeriod(Integer.parseInt(childElement.getTextContent()));
                                break;
                            case "type":                                
                               fish.setType(parseType(childElement));                            
                                break;
                            case "speed":
                                fish.setSpeed(Integer.parseInt(childElement.getTextContent()));
                            case "location":
                                fish.setLocation(parseLocation(childElement));
                                break;
                            case "smell":
                                fish.setSmell(Integer.parseInt(childElement.getTextContent()));
                                break;
                            case "lifeTime":
                                fish.setLifeTime(Integer.parseInt(childElement.getTextContent()));
                                break;
                            case "timeOfStarvation":
                                fish.setTimeOfStarvation(Integer.parseInt(childElement.getTextContent()));
                                break;
                            case "numberOfProgeny":
                                fish.setNumberOfProgeny(Integer.parseInt(childElement.getTextContent()));
                                break;
                            case "number":
                                fish.setNumber(Integer.parseInt(childElement.getTextContent()));
                                break;
                        }
                    }
                }
                
                if (name.equals("fish")) {
                    fishes.add(fish);
                    oceanConfig.setFishes(fishes);
                }
            }
        }
    }
     
    /**
     * парсинг списка еды
     *
     * @param someFood еда
     * @throws NullPointerException
     */
    private void parseFood(Element someFood) throws NullPointerException {
        
        ArrayList<Food> foods = new ArrayList<>();
        
        String name = null;
        if (someFood.getTagName().equals("foods")) {
            name = "food";
        }
        NodeList foodNodeList = someFood.getElementsByTagName(name);
        for (int i = 0; i < foodNodeList.getLength(); i++) {
            if (foodNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element foodElement = (Element) foodNodeList.item(i);
                Food food = new Food();
                NodeList childNodes = foodElement.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNodes.item(j);
                        switch (childElement.getNodeName()) {                           
                            
                            case "location":
                                food.setLocation(parseLocation(childElement));
                                break;                            
                            case "number":
                                food.setNumber(Integer.parseInt(childElement.getTextContent()));
                                break;
                        }
                    }
                }
                
                if (name.equals("food")) {
                    foods.add(food);
                    oceanConfig.setFoods(foods);
                }
            }
        }
    }

    /**
     * парсинг типа рыбы
     *
     * @param node
     * @throws NullPointerException
     */
    private TYPE parseType(Element node) {
        switch (node.getTextContent()) {
            case "fish":
                return TYPE.fish;                
            case "shark":
               return TYPE.shark;
        }
        return TYPE.fish;//по умолчанию обычная рыба
    }
    
    /**
     * парсинг замкнутости океана
     *
     * @param node
     * @throws NullPointerException
     */
    private boolean parseIsClosed(Element node) {
        switch (node.getTextContent()) {
            case "false":
                return false;                
            case "true":
               return true;
        }
        return false;//по умолчанию незамкнуто
    }
    
    /**
     * парсинг местоположения
     *
     * @param locationElement 
     * @return местоположение элемента
     */
    private Location parseLocation(Element locationElement) {
        Location location = new Location();
        int x = 0;
        int y = 0;
        NodeList locationChildNodes = locationElement.getChildNodes();
        for (int j = 0; j < locationChildNodes.getLength(); j++) {
            if (locationChildNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) locationChildNodes.item(j);
                switch (childElement.getNodeName()) {
                    case "x":                       
                        x = Integer.parseInt(childElement.getTextContent());
                        break;
                    case "y":
                        y = Integer.parseInt(childElement.getTextContent());
                        break;
                }
            }
        }
        location.x = x;
        location.y = y;
        return location;
    }
     
    /**
     * парсинг ширины поля океана
     *
     * @param oceanMaxX элемент
     
     */
    private void parseOceanMaxX(Element oceanMaxX) {
        oceanConfig.setMaxX(Integer.parseInt(oceanMaxX.getTextContent()));
    }
    
     
    /**
     * парсинг длины поля океана
     *
     * @param oceanMaxX элемент
     
     */
    private void parseOceanMaxY(Element oceanMaxY) {
        oceanConfig.setMaxY(Integer.parseInt(oceanMaxY.getTextContent()));
    }
    
    /**
     * парсинг списка течений
     *
     * @param flowElement 
     
     */
    private void parseOceanFlow(Element flowElement) {
        ArrayList<DIRECTION> flows = new ArrayList<>();
        NodeList flowList = flowElement.getElementsByTagName("flow");
        for (int i = 0; i < flowList.getLength(); i++) {
            if (flowList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element oceanFlowElement = (Element) flowList.item(i);
                flows.add(DIRECTION.valueOf(oceanFlowElement.getTextContent()));
            }
        }
        oceanConfig.setFlowList(flows);
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
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            document.setXmlStandalone(true);
            writeElements(document, systemInfoWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();           
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(outputStream));
        } catch (ParserConfigurationException | TransformerException e) {
            e.getMessage();
        }
    }

    /**
     * создание документа и запись информации
     *
     * @param document Document
     * @param oConfig OceanConfig
     *
     */
    private void writeElements(Document document, SystemInfoWriter systemInfoWriter) {
        Element oceanMonitoring = document.createElement("monitoringDOM");
        document.appendChild(oceanMonitoring); 
        for (int i = 0; i < systemInfoWriter.reports.size(); i++) {
            Element report = document.createElement("report"); 
            oceanMonitoring.appendChild(report);
            for (int j = 0; j < systemInfoWriter.reports.get(i).getDataList().size(); j++) {
                Element name = document.createElement("name");
                Element value = document.createElement("value");
                report.appendChild(name);
                name.appendChild(document.createTextNode(systemInfoWriter.reports.get(i).getDataList().get(j).getName()));
                value.appendChild(document.createTextNode(String.valueOf(systemInfoWriter.reports.get(i).getDataList().get(j).getValue())));
                report.appendChild(value);
            }
        }
    }
    
    
    
}
