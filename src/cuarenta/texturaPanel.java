/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Mtech
 */
public class texturaPanel extends JPanel{

    Image imagen;

    public texturaPanel() {
        if ((getClass().getResource("/cuarenta/Imagenes/texturaMesa.jpg")) != null)
        {
            imagen = new ImageIcon(getClass().getResource("/cuarenta/Imagenes/texturaMesa.jpg")).getImage();
           setSize(getPreferredSize());            
        }
    
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        Dimension d=getPreferredSize();
        int x=d.height;
        int y=d.width;
         g2.drawImage(imagen, 0, 0,y, x, null);
      
        
    }
    
    
}
