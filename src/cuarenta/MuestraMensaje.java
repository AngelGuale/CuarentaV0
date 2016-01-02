/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mtech
 */
public class MuestraMensaje extends TimerTask {
  

    public MuestraMensaje() {
      
    }
    
    
    @Override
    public void run() {
     
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MuestraMensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
