/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Mtech
 */

@XmlRootElement
public class Carta extends Observable implements Comparable<Carta>{
    private int numero;
    private int palo;
    //1 cr 2 br 3 cn 4 tr
    boolean select;

   private CartaButton cb;
    private ImageIcon im;
    private int ganancia;
    public  enum  Jugada{LLEVA, LLEVA_SUMA, TIRA};
    private Jugada jugada;
    
    public Jugada getJugada() {
        return jugada;
    }

    public void setJugada(Jugada jugada) {
        this.jugada = jugada;
    }
    
    
    protected ImageIcon getIm() {
        return im;
    }

    public void setIm(ImageIcon im) {
        this.im = im;
    }

    public void setCb(CartaButton cb) {
        this.cb = cb;
    }

    protected CartaButton getCb() {
        return cb;
    }

    public int getGanancia() {
        return ganancia;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }

    public Carta() {
    }
    
    public Carta(int numero, int palo) {
        this.numero = numero;
        this.palo = palo;
        this.select=false;
        
    }
    
    
    public static ArrayList <Carta> inicializaNaipe(){
    ArrayList naipe =new ArrayList();
    for(int i=1; i<=10; i++){
        for(int j=1; j<=4; j++ ){
            Carta c=new Carta(i, j);
            naipe.add(c);
        }
    
    }
   
    return naipe;
    }

 
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPalo() {
        return palo;
    }
    

    public void setPalo(int palo) {
        this.palo = palo;
    }

    @Override
    public String toString() {
        
        return "C{" + "#" + numero + ", P" + palo + '}';
    }
    public void cambio(){
    setChanged();
    }

    @Override
    public int compareTo(Carta o){
        if(this.numero<o.getNumero()){
        return -1;
        }else if(this.numero==o.getNumero()) return 0;
       else if(this.numero>o.getNumero()) return 1;
        return 0;
    }

   
   
}
