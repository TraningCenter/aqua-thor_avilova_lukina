
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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author Mariya
 */

/**
 * Класс для StAX-парсинга
 */
public class XMLParserStAX implements IParser{
    /**
     * read чтение XML-файла
     *
     * @param config input stream
     * @return oceanConfig
     */
    @Override
    public OceanConfig read(InputStream config) {
        OceanConfig oceanConfig = new OceanConfig();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        boolean maxXFlag = false, maxYFlag = false, isClosedFlag = false;
        String name;
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(config);
            int event = reader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        name = reader.getLocalName();
                        if (name.equals("ocean")) {
                        } else if (name.equals("maxX")) {
                            maxXFlag = true;
                        } else if (name.equals("maxY")) {
                            maxYFlag = true;
                        } else if (name.equals("isClosed")) {
                            isClosedFlag = true;
                        } else if (reader.getLocalName().equals("flows")) {
                            oceanConfig.setFlowList(getFlows(reader));
                            if (oceanConfig.getFlowList() == null) {
                                return null;
                            }
                        } else if (reader.getLocalName().equals("fishes")) {
                            oceanConfig.setFishes(getFishes(reader));
                            if (oceanConfig.getFishes() == null) {
                                return null;
                            } 
                        } else if (reader.getLocalName().equals("foods")) {
                            oceanConfig.setFoods(getFoods(reader));
                            if (oceanConfig.getFood() == null) {
                                return null;
                            } 
                        } else {
                            return null;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (maxXFlag) {
                            oceanConfig.setMaxX(Integer.parseInt(reader.getText()));
                            maxXFlag = false;
                        } else if (maxYFlag) {
                            oceanConfig.setMaxY(Integer.parseInt(reader.getText()));
                            maxYFlag = false;
                        } else if (isClosedFlag) {
                            oceanConfig.setIsClosed(Boolean.parseBoolean(reader.getText()));
                            isClosedFlag = false;
                        }
                        break;
                }
                if (!reader.hasNext()) {
                    break;
                }
                event = reader.next();
            }
        } catch (XMLStreamException ex) {
            return null;
        }       
        return oceanConfig;
    }
    
     /**
     * парсинг течений
     *
     * @param reader XMLStreamReader
     * @return список течений
     * @throws XMLStreamException
     * @throws IllegalArgumentException
     */
    private ArrayList<DIRECTION> getFlows(XMLStreamReader reader) throws XMLStreamException, IllegalArgumentException {
        ArrayList<DIRECTION> flows = new ArrayList<>();
        boolean flowFlag = false;
        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equals("flow")) {
                        flowFlag = true;
                    } else {
                        return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (flowFlag) {
                        flows.add(DIRECTION.valueOf(reader.getText()));
                        flowFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("flows")) {
                        return flows;
                    }
                    break;
            }
        }
        return null;
    }
    
     /**
     * парсинг списка рыб
     *
     * @param reader XMLStreamReader     
     * @return список рыб
     * @throws XMLStreamException
     */
    private ArrayList<Fish> getFishes(XMLStreamReader reader) throws XMLStreamException {
        ArrayList<Fish> fishes = new ArrayList<>();
        boolean lifeTimeFlag = false, progenyPeriodFlag = false, smellFlag = false, speedFlag = false, typeFlag = false, timeOfStarvationFlag = false, 
                numberOfProgenyFlag = false, numberFlag = false;
        Fish fish = new Fish();       
        String name;
        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals("fish")) {
                    } else if (name.equals("lifeTime")) {
                        lifeTimeFlag = true;
                    } else if (name.equals("progenyPeriod")) {
                        progenyPeriodFlag = true;
                    } else if (name.equals("smell")) {
                        smellFlag = true;
                    } else if (name.equals("speed")) {
                        speedFlag = true;
                    } else if (name.equals("type")) {
                        typeFlag = true;
                    } else if (name.equals("timeOfStarvation")) {
                        timeOfStarvationFlag = true;
                    } else if (name.equals("numberOfProgeny")) {
                        numberOfProgenyFlag = true;
                    } else if (name.equals("number")) {
                        numberFlag = true;
                    } else if (reader.getLocalName().equals("location")) {
                        fish.setLocation(getLocation(reader));
                        if (fish.getLocation() == null) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (lifeTimeFlag) {
                        fish.setLifeTime(Integer.parseInt(reader.getText()));
                        lifeTimeFlag = false;
                    } else if (progenyPeriodFlag) {
                        fish.setProgenyPeriod(Integer.parseInt(reader.getText()));
                        progenyPeriodFlag = false;
                    } else if (smellFlag) {
                        fish.setSmell(Integer.parseInt(reader.getText()));
                        smellFlag = false;
                    } else if (speedFlag) {
                        fish.setSpeed(Integer.parseInt(reader.getText()));
                        speedFlag = false;
                    } else if (typeFlag) {                        
                        fish.setType(TYPE.valueOf(reader.getText()));
                        typeFlag = false;
                    } else if (timeOfStarvationFlag) {
                        fish.setTimeOfStarvation(Integer.parseInt(reader.getText()));
                        timeOfStarvationFlag = false;
                    } else if (numberOfProgenyFlag) {
                        fish.setNumberOfProgeny(Integer.parseInt(reader.getText()));
                        numberOfProgenyFlag = false;
                    } else if (numberFlag) {
                        fish.setNumber(Integer.parseInt(reader.getText()));
                        numberFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals("fish")) {
                        fishes.add(fish);
                        fish = new Fish();                       
                    } else if (name.equals("fishes")) {
                        return fishes;
                    }
                    break;
            }
        }
        return null;
    }
    
    /**
     * парсинг еды
     *
     * @param reader XMLStreamReader
     * @return список еды
     * @throws XMLStreamException
     * @throws IllegalArgumentException
     */
    private ArrayList<Food> getFoods(XMLStreamReader reader) throws XMLStreamException, IllegalArgumentException {
        ArrayList<Food> foods = new ArrayList<>();
        boolean numberFlag = false;
        Food food = new Food();
        String name;
        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals("food")) {
                    } else if (name.equals("number")) {
                        numberFlag = true;
                    } else if (reader.getLocalName().equals("location")) {
                        food.setLocation(getLocation(reader));
                        if (food.getLocation() == null) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (numberFlag) {
                        food.setNumber(Integer.parseInt(reader.getText()));
                        numberFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals("food")) {
                        foods.add(food);                       
                        food = new Food();                       
                    } else if (name.equals("foods")) {
                        return foods;
                    }
                    break;
            }
        }
        return null;
    }
    
     /**
     * парсинг местоположения
     *
     * @param reader XMLStreamReader
     * @return location
     * @throws XMLStreamException
     */
    private Location getLocation(XMLStreamReader reader) throws XMLStreamException {
        Location location = new Location();
        boolean xFlag = false, yFlag = false;
        int event = reader.getEventType();
        while (reader.hasNext()) {
            event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (reader.getLocalName()) {
                        case "x":
                            xFlag = true;
                            break;
                        case "y":
                            yFlag = true;
                            break;
                        default:
                            return null;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (xFlag) {
                        location.x = Integer.parseInt(reader.getText());
                        xFlag = false;
                    } else if (yFlag) {
                        location.y = Integer.parseInt(reader.getText());
                        yFlag = false;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("location")) {
                        return location;
                    }
                    break;
            }
        }
        return null;
    }
    
       
    
      /**
     * запись информации в XML-файл
     *
     * @param outputStream OutputStream
     * @param systemInfoWriter SystemImfoWriter
     *
     */
    @Override
    public void write(SystemInfoWriter systemInfoWriter, OutputStream outputStream) {
        try {             
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter staxWriter = factory.createXMLStreamWriter(outputStream);
            staxWriter.writeStartDocument();            
            staxWriter.writeStartElement("monitoringStAX");
            for (int i = 0; i < systemInfoWriter.reports.size(); i++) {
                staxWriter.writeStartElement("report");  
                for (int j = 0; j < systemInfoWriter.reports.get(i).getDataList().size(); j++) {                   
                    staxWriter.writeStartElement("name");
                    char[] tempName = String.valueOf(systemInfoWriter.reports.get(i).getDataList().get(j).getName()).toCharArray();
                    staxWriter.writeCharacters(tempName, 0, tempName.length);
                    staxWriter.writeEndElement();
                    staxWriter.writeStartElement("value");
                    char[] tempValue = String.valueOf(systemInfoWriter.reports.get(i).getDataList().get(j).getValue()).toCharArray();
                    staxWriter.writeCharacters(tempValue, 0, tempValue.length);
                    staxWriter.writeEndElement();                    
                }               
                staxWriter.writeEndElement();
            }
            staxWriter.writeEndElement();           
            staxWriter.flush();
            staxWriter.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
