
package com.mycompany.aguathor;

import com.mycompany.aguathor.data.OceanConfig;
import com.mycompany.aguathor.controller.FoodsController;
import com.mycompany.aguathor.controller.FishController;
import com.mycompany.aguathor.model.Fish;
import com.mycompany.aguathor.ui.PrintOcean;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.googlecode.lanterna.TerminalFacade;



import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;

import com.mycompany.aguathor.parsers.XMLParserDOM;
import com.mycompany.aguathor.parsers.IParser;
import com.mycompany.aguathor.parsers.XMLParserJAXB;
import com.mycompany.aguathor.parsers.XMLParserSAX;
import com.mycompany.aguathor.parsers.XMLParserStAX;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Mariya
 */
public class SystemController {
    Ocean ocean;
    PrintOcean renderer;
    ExecutorService executor;   

    boolean stopProcess = false;
    boolean startProcess = true;
    SystemInfoWriter systemInfoWriter;
    OceanConfig oceanConfig;
    
    Screen screen = TerminalFacade.createScreen();
      
    public SystemController() {
    }
    
    
    /**
     * запуск работы системы
     */
    public void start() throws IOException {
        try {
            renderer = new PrintOcean();
            renderer.setScreen(screen);
            renderer.startScreen();
        } catch (IOException ex) {
            ex.getMessage();
        }
        oceanConfig = readXML();
        ocean = new Ocean(oceanConfig, new FoodsController(oceanConfig.getFood().size(), oceanConfig.getFood(), oceanConfig.getMaxX(), oceanConfig.getMaxY()));
        Ocean.ocean = ocean;
        PrintOcean print = new PrintOcean(renderer.getScreen(), ocean.getMaxX(), ocean.getMaxY(), ocean);  
        ArrayList <FishController> fishControllerList = new ArrayList<FishController>();
        for (Fish fish: oceanConfig.getFishes()){
            fishControllerList.add(new FishController(fish, ocean));
        }
        MainController mainController = new MainController(fishControllerList, ocean.getMaxX(), ocean.getMaxY(), print);
        executor = Executors.newFixedThreadPool(2);  
        
        /*******/
        systemInfoWriter = new SystemInfoWriter();
        executor.submit(() -> {
            stopProcess = false;           
                try {                     
                     mainController.live(stopProcess, systemInfoWriter);
                } catch (Exception ex) {
                    ex.getMessage();
                }
        });
        
        /*******/
             
        while (startProcess) {
            Key key;
            try {
                key = renderer.getScreen().readInput();                
                if (key != null && key.getKind() == Key.Kind.Enter) {
                    stopProcess = true;
                    executor.submit(() -> {
                        try {
                            writeXML();
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    });
                    executor.shutdown();                    
                }
            } catch (Exception ex) {
                ex.getMessage();
            }
        }
    }
    
    /**
     * Чтение xml-файла с исходными данными
     *
     * @return конфигурацию океана
     * @throws IOException
     */
    private OceanConfig readXML() throws IOException {
        InputStream inputStream = new FileInputStream("config.xml");
        String parserName = getParserName("config.properties", "inputparser");
        IParser parser = null;
        switch (parserName) {
            case "dom":
                parser = new XMLParserDOM();
                break;
            case "sax":
                parser = new XMLParserSAX();
                break;
            case "stax":
                parser = new XMLParserStAX();
                break;
            case "jaxb":
                parser = new XMLParserJAXB();
                break;           
        }
        OceanConfig config = parser.read(inputStream);
        inputStream.close();
        return config;
    }


    /**
     * Запись данных в xml-файл
     *
     * @throws IOException
     */
    private void writeXML() throws IOException {        
        
        String parserName = getParserName("config.properties", "outputparser");
        OutputStream outputStream = new FileOutputStream("output.xml");
        IParser parser = null;
        switch (parserName) {
            case "dom":
                parser = new XMLParserDOM();
                break;
            case "sax":
                parser = new XMLParserSAX();
                break;
            case "stax":
                parser = new XMLParserStAX();
                break;
            case "jaxb":
                parser = new XMLParserJAXB();
                break;     
        } 
        systemInfoWriter.writeData();
        parser.write(systemInfoWriter, outputStream);
        outputStream.close();
    }

     /**
     * Получение названия парсера 
     *
     * @param propertiesFile
     * @param key
     * @return value
     * @throws java.io.IOException
     */
    private String getParserName(String propertiesFile, String key) throws IOException {
        Properties properties = new Properties();
        String parserName;
        properties.load(new FileInputStream(propertiesFile));
        parserName = properties.getProperty(key);
        return parserName;
    }    
}
