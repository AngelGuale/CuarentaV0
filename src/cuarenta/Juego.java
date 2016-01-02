/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mtech
 */

@XmlRootElement

public class Juego {

    
    
    public enum Situacion{Caida ,Limpia, CaidaEnRonda ,  CaidaYLimpia, CaidaYLimpiaEnRonda, Nada}
   
            
    private ArrayList <Carta> naipe;
    private Jugador jugador;
    private Computador pc;
    private int turno;
    private ArrayList <Carta> mesa;
    Scanner sc=new Scanner(System.in);
    private ArrayList <Carta> caidas;
    protected boolean carrera;
    protected ModoCarrera modoCarrera;
    
    public int nivel;
    

    public ArrayList<Carta> getCaidas() {
        return caidas;
    }

    public void setCaidas(ArrayList<Carta> caidas) {
        this.caidas = caidas;
    }

    public ArrayList<Carta> getMesa() {
        return mesa;
    }

    public void setMesa(ArrayList<Carta> mesa) {
        this.mesa = mesa;
    }
    
    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public ArrayList<Carta> getNaipe() {
        return naipe;
    }

    public void setNaipe(ArrayList<Carta> naipe) {
        this.naipe = naipe;
    }

    public Computador getPc() {
        return pc;
    }

    public void setPc(Computador pc) {
        this.pc = pc;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Juego() {
        naipe=Carta.inicializaNaipe();
        this.barajaNaipe();
        cargaImagenes();
        jugador=new Jugador();
        pc=new Computador();
        mesa=new ArrayList();
        caidas=new ArrayList();
        
        jugador.setJuego(this);
        pc.setJuego(this);
    
        jugador.addObserver(pc);
    }
    public Juego(boolean carrera) {
        naipe=Carta.inicializaNaipe();
        this.barajaNaipe();
        cargaImagenes();
        jugador=new Jugador();
        pc=new Computador();
        mesa=new ArrayList();
        caidas=new ArrayList();
        
        jugador.setJuego(this);
        pc.setJuego(this);
    
        jugador.addObserver(pc);
        
        this.carrera=carrera;
        
    }
    
    
    private void barajaNaipe(){
        
        for(int i=0; i<10; i++){
            Collections.shuffle(naipe);
        }
    }
    

     

        public void culminaJuego(){
       caidas.clear();
       jugador.getMano().clear();
       getPc().getMano().clear();
       
           }
        private void cargaImagenes(){
        String nIm;
            for(Carta c: naipe){
                nIm="";
                if(c.getPalo()==1)
                nIm=c.getNumero()+"tr";
                else if(c.getPalo()==2)
                    nIm=c.getNumero()+"cr";
                else if(c.getPalo()==3)
                    nIm=c.getNumero()+"cn";
                else if(c.getPalo()==4)
                    nIm=c.getNumero()+"br";
                
                    c.setIm(new ImageIcon(getClass().getResource("/cuarenta/Imagenes/"+nIm+".png")));
    
    }
        }
           
        
     
        public boolean movimiento(Carta c, FrMesa fr, Jugador jug)
        {

            int suma=0;
        ArrayList <Carta> marcadas=new ArrayList();

        for(Carta k: mesa){
            if(k.select){
            suma=suma+k.getNumero();
            marcadas.add(k);
            }
    }
        c.getCb().arriba=false;
        
        if(suma==0){  
        CartaButton vacia=fr.buscaVacia();

       this.getMesa().add(c);//añade carta a la mesa
       
       caidas.add(c); //añade carta a caidas
       
       jug.getMano().remove(c);//saca la carta de la mano del jugador
       
       
       jug.setUltima(c);//ultima carta
       
       vacia.carta=c;//enlaza carta mesa con cartabutton mesa
       
       c.getCb().carta=null;
       c.cambio();
       c.notifyObservers();
       
       c.deleteObservers();//borra observadores de la carta
       
       
       c.addObserver(vacia); //añade el nuevo observador en mesa
       
       c.cambio();
       c.notifyObservers();
        
       c.setCb(vacia);
        
       
            return true;
        }
        
      
        if(this.isEscalera(marcadas, c)){
            actualizaPuntos(c, marcadas, jug, true);
            
            for(Carta m: marcadas){
            getMesa().remove(m);
                jug.getLlevadas().add(m);
           
            // caidas.add(m);
            
            m.select=!m.select;
        
            m.getCb().carta=null;
           
            m.cambio();
            
            m.notifyObservers();
       
            m.deleteObservers();
            
            m.setCb(null); 
        }
        jug.getMano().remove(c); //saca de la man0
        caidas.add(c);//añande carta a caidas
        
        jug.getLlevadas().add(c);// la pone en llevadas
        jug.setUltima(c);//ultima carta
        
        
       c.getCb().carta=null;//  cb.carta=null;

        c.cambio();
        c.notifyObservers();
        c.deleteObservers();
        c.setCb(null);
       
        return true;

        }    

        if(marcadas.size()>1&&isEscaleraSuma(marcadas, c) ){
        
            
            actualizaPuntos(c, marcadas, jug, false);
            
            for(Carta m: marcadas){
            getMesa().remove(m);
           // caidas.add(m);//añade carta a caidas
            jug.getLlevadas().add(m);
            
            m.select=!m.select;
           m.getCb().carta=null;
            m.cambio();
            m.notifyObservers();
            m.deleteObservers();
            m.setCb(null); 
             }
     
        jug.getMano().remove(c);
        caidas.add(c);//añade carta a caidas
        jug.getLlevadas().add(c);
        jug.setUltima(c);

      c.getCb().carta=null;//  cb.carta=null;

      c.cambio();
        c.notifyObservers();
        c.deleteObservers();
        c.setCb(null);

        return true;
        }else{
        return false;
        }

    }

        
    public boolean isEscaleraSuma(ArrayList <Carta> marcadas, Carta carta){
   ArrayList <Carta> m2=marcadas;
        Collections.sort(m2);
        int suma=m2.get(0).getNumero()+m2.get(1).getNumero();
   int dif;
   if(suma==carta.getNumero() && suma<=7){
       if(m2.size()>2 && (m2.get(2).getNumero()-suma)!=1) return false;
   for(int i=3; i<m2.size(); i++){
       dif=m2.get(i).getNumero()-m2.get(i-1).getNumero();
       if(dif!=1){
         return false;}
   }
   return true;
   }
   return false;
    }
    
     
    public boolean isEscalera(ArrayList <Carta> marcadas, Carta carta){
   ArrayList <Carta> m2=marcadas;
        Collections.sort(m2);
   int dif;
   if(carta.compareTo(m2.get(0))==0){
   for(int i=1; i<m2.size(); i++){
       dif=m2.get(i).getNumero()-m2.get(i-1).getNumero();
       if(dif!=1){
         return false;}
   }
   return true;
   }
   return false;
    }
    

   public boolean isLimpia(){
   if(  this.getMesa().isEmpty()) return true;
   else return false;
       
   }
   public boolean seraLimpia(ArrayList <Carta> marcadas){
   if(getMesa().size()==marcadas.size()) return true;
   return false;
   }
   
    public boolean isCaida(Jugador oponente, Carta marcada){
    if(oponente.getUltima()!=null){
        if(oponente.getUltima().equals(marcada)){
  
            return true;
    
    }
    }
    return false;
    }
    
public boolean isRonda(Jugador j){
     ArrayList <Carta> mano= (ArrayList<Carta>) j.getMano().clone();
    // JOptionPane.showMessageDialog(null, "is ronda");
     Collections.sort(mano);
     int k1=0;
     int k2;
     int cont=1;
     for(Carta c: mano){
     k2=c.getNumero();
     
     if(k2==k1){cont++;}
     else{
     k1=k2;
     cont=1;
     }
     
     if(cont>=3){
       JOptionPane.showMessageDialog(null, "RONDA");
         return true;
     }
     }
     return false;
}       
    public Jugador getOponente(Jugador j){
    if(j.equals(getJugador())) return getPc();
    else if(j.equals(getPc())) return getJugador();
    return null;
    }
    public void reparteCartas(){
     for(int i=0; i<5; i++){
         Carta c= this.naipe.remove(0);
         jugador.getMano().add(c);
      }
      for(int i=0; i<5; i++){
         Carta c= this.naipe.remove(0);
         pc.getMano().add(c);
      }
        
    }
    
    private void configuraRonda(Jugador j) {
      ArrayList <Carta> mano= (ArrayList<Carta>) j.getMano();
    // JOptionPane.showMessageDialog(null, "is ronda");
     Collections.sort(mano);
    
    for(int i=1; i<4; i++){
        if(mano.get(i).compareTo(mano.get(i-1))==0 && mano.get(i).compareTo(mano.get(i+1))==0){
        j.getRonda().add(mano.get(i-1));
        j.getRonda().add(mano.get(i));
        j.getRonda().add(mano.get(i+1));
        return;
        }
    }
    
      
    }
    

    public void repartir(FrMesa fr){
    
    if(this.getJugador().getMano().isEmpty() && this.getPc().getMano().isEmpty()){
         
        getJugador().getRonda().clear();
         getPc().getRonda().clear();
         
         
        if(!getNaipe().isEmpty()){
        
            reparteCartas();
                
                fr.actualizaManosButton();
             verificaRondaEnRepartir(jugador, fr);   
               
                verificaRondaEnRepartir(pc, fr);
   
                
        }
        
        else{
        
        fr.cuentaCarton();
        
        if(!fr.jugando) return;
        
        naipe=Carta.inicializaNaipe();
        cargaImagenes();
        this.barajaNaipe();
        caidas.clear();
        repartir(fr); 
        
        }
        getJugador().setUltima(null);
         getPc().setUltima(null);
    }
    
    } 
    public void verificaRondaEnRepartir(Jugador j, FrMesa fr){
    if(isRonda(j)){
                    
                    configuraRonda(j);
            if(!j.esta38){
                    System.out.println("ronda j"+j.getRonda());
        j.setPuntos(j.getPuntos()+2);
            
        fr.actualizaPuntosLabel(j);
            
            }    
        }
    }
    
    public int hayGanador(){
    if( getJugador().getPuntos()>=40){
    return 1;
    }else{
            if(getJugador().getPuntos()==38){
                if(getJugador().esta38==false){
                    getJugador().esta38=true;
                JOptionPane.showMessageDialog(null, "¡38 QUE NO JUEGA!\nJugador está en 38 puntos, \n gana con caida");
                                        }
            }
        }
    if(getPc().getPuntos()>=40){
    return 2;
    }
    else{
    if(getPc().getPuntos()==38){
        if(getPc().esta38==false){
    getPc().esta38=true;
    JOptionPane.showMessageDialog(null, "¡38 QUE NO JUEGA!\nPc está en 38 puntos, \n gana con caida");
    }
    }
    }
    return 0;   
    
    }
    
  
public void reiniciaLlevadas(Jugador j){

    j.getLlevadas().clear();
}    

public void reiniciaMesa(){
    for(Carta c: mesa){
    c.getCb().carta=null;
    c.cambio();
    c.notifyObservers();
    c.deleteObservers();
    c.setCb(null);
    }

    mesa.clear();
}
public boolean isCaidaEnRonda(Jugador oponente, Carta marcada){
  if(oponente.getRonda()!=null || !oponente.getRonda().isEmpty()){
    if(oponente.getRonda().contains(marcada))
    return true;
  }
  
    return false;
}

public Situacion ObtieneSituacion(Carta tirada, ArrayList<Carta> marcadas, Jugador j, boolean isEscalera){
    int indicador;
    Situacion s;
    if(isCaida(getOponente(j),marcadas.get(0)) && isEscalera){
    s= Situacion.Caida;
        if(isCaidaEnRonda(getOponente(j), marcadas.get(0))){
            s= Situacion.CaidaEnRonda;
            if(seraLimpia(marcadas)){
                s=Situacion.CaidaYLimpiaEnRonda;
                }
        }
        if(seraLimpia(marcadas)){
        s=Situacion.CaidaYLimpia;
        }
        return s;
    }
    if(seraLimpia(marcadas)){
    s=Situacion.Limpia;
    return s;
    }
   return Situacion.Nada;
    

}
public void actualizaPuntos(Carta tirada, ArrayList<Carta> marcadas, Jugador j, boolean escalera){
    Situacion s=ObtieneSituacion(tirada, marcadas, j, escalera);
    if(!j.esta38){
        
    switch(s){
        case Caida:
            //muestra mensaje
             muestraMsjJOP("Caida\n +2 Puntos");
         j.setPuntos(j.getPuntos()+2);  
            break;
        case CaidaEnRonda:   
            //muestra mensaje
            muestraMsjJOP("Caida En Ronda\n +4 Puntos");
               j.setPuntos(j.getPuntos()+4);   
            break;
        case CaidaYLimpia:
                        muestraMsjJOP("Caida Y Limpia\n +4 Puntos");
             j.setPuntos(j.getPuntos()+4);   
            break;
         case  CaidaYLimpiaEnRonda:
               muestraMsjJOP("Caida en Ronda Y Limpia\n +6 Puntos");
             j.setPuntos(j.getPuntos()+6); 
             break;
         case Limpia:
              muestraMsjJOP("Limpia\n +2 Puntos");
             j.setPuntos(j.getPuntos()+2); 
            break;
             
    }
    }
    else{
     switch(s){
    case Caida:
         muestraMsjJOP("Caida\n +2 Puntos");
            j.setPuntos(j.getPuntos()+2); 
            break;
    }
    }
    
        }
public void muestraMsjJOP(String mensaje){
JOptionPane.showMessageDialog(null, mensaje);
}

   
}



