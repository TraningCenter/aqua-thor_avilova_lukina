
package com.mycompany.aguathor;

import java.io.IOException;


public class Main {
    
    public static void main(String... args) throws InterruptedException, IOException {
      
      
      SystemController systemController = new SystemController();
      
      systemController.start();      
      systemController.screen.stopScreen();
      System.exit(0);  
    }
            
}
