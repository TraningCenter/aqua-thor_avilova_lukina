
package com.mycompany.aguathor;

import com.mycompany.aguathor.Ocean;
import com.mycompany.aguathor.data.Report;
import com.mycompany.aguathor.data.OutputData;
import com.mycompany.aguathor.data.OutputData;
import com.mycompany.aguathor.data.Report;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mariya
 */

/**
 * Класс для записи выходных данных о системе
 */
@XmlRootElement(name = "systeminfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class SystemInfoWriter {
    @XmlElement(name = "report")
    public ArrayList<Report> reports = new ArrayList();

     /**
     * Конструктор
     *
     * @param reports список отчетов с информацией о системе
     *
     */
    public SystemInfoWriter(ArrayList<Report> reports) {
        this.reports = reports;        
    }

    SystemInfoWriter() {        
    }
    
    /**
     * Запись данных в список отчетов
     */
    public void writeData() {        
        ArrayList<OutputData> dataList = new ArrayList();        
        dataList.add(new OutputData("CountOfGrassFeedingFishes", Ocean.ocean.getGrassFeedingFishes().size()));
        dataList.add(new OutputData("SharksCount", Ocean.ocean.getSharks().size()));       
        reports.add(new Report(dataList));      
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }
    
}
