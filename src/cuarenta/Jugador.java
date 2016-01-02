/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.util.ArrayList;
import java.util.Observable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mtech
 */

@XmlRootElement

public class Jugador extends Observable{
    private int puntos;
    private ArrayList <Carta> mano;
    private ArrayList <Carta> llevadas;
    private Carta ultima;
    private boolean jugo;
    private Juego juego;
    public boolean esta38;
    private ArrayList<Carta> ronda;
    
    public ArrayList<Carta> getRonda() {
        return ronda;
    }

    public void setRonda(ArrayList<Carta> ronda) {
        this.ronda = ronda;
    }
    
    
    public Carta getUltima() {
        return ultima;
    }

    public void setUltima(Carta ultima) {
        this.ultima = ultima;
    }
    
    public Jugador(){
        mano = new ArrayList();
        llevadas = new ArrayList();
        esta38=false;
        ronda=new ArrayList<Carta>();
    }

    public ArrayList<Carta> getLlevadas() {
        return llevadas;
    }

    public void setLlevadas(ArrayList<Carta> llevadas) {
        this.llevadas = llevadas;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    protected Juego getJuego() {
        return juego;
    }

    
    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public boolean isJugo() {
        return jugo;
    }

    public void setJugo(boolean jugo) {
        this.jugo = jugo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    public  void jugCambio(){
    setChanged();
    }
    
    public boolean esta38QNoJuega(){
    if(this.getPuntos()==38){
    esta38=true;
    return true;
    }
    return false;
    
    }
}
