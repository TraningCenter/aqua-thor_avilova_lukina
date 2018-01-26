
package com.mycompany.aguathor.parsers;

/**
 *
 * @author Mariya
 */
import com.mycompany.aguathor.data.OceanConfig;
import com.mycompany.aguathor.SystemInfoWriter;
import java.io.InputStream;
import java.io.OutputStream;

public interface IParser {

    OceanConfig read(InputStream config);
    void write(SystemInfoWriter systemInfoWriter, OutputStream outputStream);
}
