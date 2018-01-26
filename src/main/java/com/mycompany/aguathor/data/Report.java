
package com.mycompany.aguathor.data;

import com.mycompany.aguathor.data.OutputData;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 *
 * @author Mariya
 */


/**
 * Класс для хранения списка выходных данных
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {    
  
    private ArrayList<OutputData> dataList;

    /**
     * Конструктор  
     * @param dataList  - список данных о системе
     *
     */
    public Report(ArrayList<OutputData> dataList) {        
        this.dataList = dataList;
    }

   

    public ArrayList<OutputData> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<OutputData> dataList) {
        this.dataList = dataList;
    }
}