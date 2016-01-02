/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Mtech
 */
public class MensajePanel extends JPanel {
    
   String mensaje;
    
    public MensajePanel() {
       
        setPreferredSize(new Dimension(75, 100));
        
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;
        
        if(mensaje!=null){
          g.setColor(Color.red); 
          
             g.fill3DRect(20, 20, 500, 500, true);
             
            g.setFont(new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, 20));
            g.setColor(Color.WHITE);
        g.drawString(mensaje, getX()+50, getY()+50);
        }
    }
    
    
    
    public static  void main(String[] s){
    MensajePanel mp=new MensajePanel();
    mp.setMensaje("Caida\n y\n Limpia");
    JFrame fr=new JFrame();
    fr.setSize(200, 200);
    fr.add(mp);
    
    fr.setVisible(true);
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
