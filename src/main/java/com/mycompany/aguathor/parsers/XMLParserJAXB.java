
package com.mycompany.aguathor.parsers;

import com.mycompany.aguathor.data.OceanConfig;
import com.mycompany.aguathor.SystemInfoWriter;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;

/**
 *
 * @author Mariya
 */

/**
 * Класс для JAXB-парсинга
 */
public class XMLParserJAXB implements IParser{
    
    /**
     * чтение XML-файла
     *
     * @param config input stream
     * @return oceanConfig
     */
    @Override
    public OceanConfig read(InputStream config) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OceanConfig.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setEventHandler((ValidationEvent event) -> false);
            OceanConfig oceanConfig = (OceanConfig) unmarshaller.unmarshal(config);            
            return oceanConfig;
        } catch (JAXBException ex) {
            return null;
        }
    }

    /**
     * запись отчета в XML-файл
     *
     * @param systemInfoWriter информация о системе
     * @param outputStream output stream
     */
    @Override
    public void write(SystemInfoWriter systemInfoWriter, OutputStream outputStream) {
        try {
            JAXBContext jc = JAXBContext.newInstance(SystemInfoWriter.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(systemInfoWriter, outputStream);
        } catch (JAXBException ex) {
             ex.printStackTrace();
        }
    }
}

