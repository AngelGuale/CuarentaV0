/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mtech
 */

@XmlRootElement

public class CartaButton extends JButton implements Observer, Comparable <CartaButton>{
    Carta carta;
    boolean select;
    Color color1;
    Border borde;
    int tipo;  // 1 para j1 2 para j2 0 para mesa
    boolean arriba;
    boolean habilitada;

    public CartaButton() {
        setPreferredSize(new Dimension(60, 75) );
        select=false;
        color1=this.getBackground();
        borde=this.getBorder();
          arriba=false;
          habilitada=true;
        addMouseListener(new MouseAdapter() {
          
            
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                
                CartaButton c=(CartaButton)e.getSource();
                 FrMesa fr=(FrMesa) c.getParent().getParent().getParent().getParent().getParent();

                if(c.carta!=null && c.habilitada && !fr.juego.turnoPC){

                        if(tipo==0){

                                if(!c.carta.select){
                                    c.carta.select=!c.carta.select;
                                        c.setBorder(new LineBorder(Color.BLUE, 2));

                                }
                                else{
                                c.setBackground(color1);
                                c.setBorder(null);
                                c.carta.select=!c.carta.select; 

                                }
                        }
                        if(c.tipo==1 ){
                      //  FrMesa fr=(FrMesa) c.getParent().getParent().getParent().getParent().getParent();

                        if(fr.juego.movimiento(c.carta, fr, fr.juego.getJugador()))
                        {

                        fr.actualizaPuntosLabel(fr.juego.getJugador());
                        fr.actualizaCartonLabel(fr.juego.getJugador());

                            if(fr.juego.hayGanador()==1){
                            JOptionPane.showMessageDialog(null, "Gano J1");
                            
                             if(fr.juego.carrera) {
                                     ModoCarrera mc=fr.juego.modoCarrera;
                           mc.setNivel(mc.getNivel()+1);
                                 fr.reiniciarJuegoCarrera();
                             }
                   else fr.suspenderJuego();
                            return;
                            }
                                fr.juego.turnoPC=true;
                                
                       // fr.juego.getJugador().jugCambio();
                       // fr.juego.getJugador().notifyObservers();
                            
                        }
                        } 
                }
                }
          
        });
        
        
     
        
    }

 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.carta!=null){
           Graphics2D g2=(Graphics2D)g;
            
           if(tipo!=2){
           
           this.setIcon(null);
           
            this.setIcon(this.carta.getIm());
           }
           
            if(tipo==2){
          //  g2.drawString(""+carta.getNumero(),20, 20);
            this.setIcon(new ImageIcon(getClass().getResource("/cuarenta/Imagenes/detras.png")));
                if(arriba){
                 this.setIcon(null);
           
                this.setIcon(this.carta.getIm());
            
                }
            }
                if(this.carta!=null){
                    if(this.carta.select)
                    setBorder(new LineBorder(Color.BLUE, 2));
                    else
                    this.setBorder(borde);
                }
         }
        else{
            if(tipo!=0){
            this.setBorder(borde);
            }else{
            this.setBorder(null);
            }
         this.setIcon(null);
         this.setIcon(new ImageIcon(getClass().getResource("/cuarenta/Imagenes/fondoMesa.jpg")));
        }
     
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
       
        repaint();
        
    }


    @Override
    public int compareTo(CartaButton o) {
        return this.carta.compareTo(o.carta);
    }
    
    }

