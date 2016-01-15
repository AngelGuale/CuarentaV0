/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Usuario
 */

public class GameState implements Comparable<GameState>{
    private ArrayList <Carta> mesa;
    private ArrayList <Carta> caidas;
    int[] base=new int[11];
    private ArrayList <Carta> mano;
    private ArrayList<DBUnit> base_db;
    //int puntos_propios;
    //int puntos_rival;
    //int carton_propio;
    //int carton_rival;
    int valoracion;
    private Estado estado;
    Carta cartaJugada;
    Carta mejorJugada;
    GameState elegido;
    Carta.Jugada jugada;
    int alpha;
    int beta;
    @Override
    public int compareTo(GameState o) {
          if(this.valoracion<o.valoracion){
        return -1;
        }else if(this.valoracion==o.valoracion) return 0;
       else if(this.valoracion>o.valoracion) return 1;
        return 0;
    }

        public enum Tipo{Min, Max}
    Tipo tipo;
    private ArrayList <Carta> mano_probable;
    
    
    public GameState(ArrayList<Carta> mesa, ArrayList<Carta> caidas, ArrayList<Carta> mano, int puntos_propios, int puntos_rival, int carton_propio, int carton_rival,Carta cartaJugada, Tipo t) {
        this.mesa = mesa;
        this.caidas = caidas;
        this.mano = mano;
        estado=new Estado();
        estado.puntos_propios = puntos_propios;
        estado.puntos_rival = puntos_rival;
        estado.carton_propio = carton_propio;
        estado.carton_rival = carton_rival;
        this.valoracion=-100;
        this.cartaJugada=cartaJugada;
        this.mejorJugada=null;
        this.tipo=t;
        
       
        if(t==Tipo.Min){
            mano_probable=new ArrayList <Carta>();
        actualizaBase();
        
        Collections.sort(this.base_db);
        /*
        for (int i=1;i<10;i++){
            if(this.base[i]<2){
                Carta c=new Carta(i, 1);
                this.mano_probable.add(c);
            }
         }
       */
      
         if(caidas.size()>10){
        for (int i=0;i<6;i++){
                DBUnit d=this.base_db.get(i);
                if(d.cuantas<4){
                        Carta c=new Carta(d.num_carta, 1);
                        this.mano_probable.add(c);
                             }
        }
     
            }
        else{
            for (int i=0;i<6;i++){
                // k = (int) (Math.random()*10);
                   DBUnit d=this.base_db.get(9-i);
                   if(d.cuantas<4){
                           Carta c=new Carta(d.num_carta, 1);
                           this.mano_probable.add(c);
                                }
            }
        }
      //este es el constructor del inicio
        }
        this.setAlphaBetaInicial();
    }
    
    
    public GameState(ArrayList<Carta> mesa, ArrayList<Carta> caidas, ArrayList<Carta> mano, Estado estado,Carta cartaJugada, Tipo t) {
        this.mesa = mesa;
        this.caidas = caidas;
        this.mano = mano;
        this.estado=estado;
        this.valoracion=-100;
        this.cartaJugada=cartaJugada;
        this.mejorJugada=null;
        this.tipo=t;
        
        if(t==Tipo.Min){
            mano_probable=new ArrayList <Carta>();
        actualizaBase();
        
        Collections.sort(this.base_db);
        /*
        for (int i=1;i<10;i++){
            if(this.base[i]<2){
                Carta c=new Carta(i, 1);
                this.mano_probable.add(c);
            }
         }
       */
        //if(false){
       if(caidas.size()>10){
        for (int i=0;i<6;i++){
                DBUnit d=this.base_db.get(i);
                if(d.cuantas<4){
                        Carta c=new Carta(d.num_carta, 1);
                        this.mano_probable.add(c);
                             }
        }
     
            }
        else{
            for (int i=0;i<6;i++){
                // k = (int) (Math.random()*10);
                   DBUnit d=this.base_db.get(9-i);
                   if(d.cuantas<4){
                           Carta c=new Carta(d.num_carta, 1);
                           this.mano_probable.add(c);
                                }
            }
        }
       //  System.out.println("Probable");
         //  System.out.println(this.base_db);
           //   System.out.println(this.mano_probable);
        }
    }
    private GameState(ArrayList<Carta> mesa, ArrayList<Carta> caidas, ArrayList<Carta> mano, int puntos_propios, int puntos_rival, int carton_propio, int carton_rival) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ArrayList<Carta> getMesa() {
        return mesa;
    }

    public ArrayList<Carta> getCaidas() {
        return caidas;
    }
    public ArrayList<Carta> getMano() {
        

        return mano;
        
}   
    
    private void setAlphaBetaInicial(){
        int v=getValoracionCruda();
        this.alpha=v-20;
        this.beta=v+20;
    }
    
    public void cloneAlphaBetaInicial(int alfa, int beta){
        
        this.alpha=alfa;
        this.beta=beta;
    }

    public ArrayList<Carta> getManoActual() {
        
        if(tipo==Tipo.Max){
        return mano;}
        else{
        return mano_probable;
        }
    }

    public void setMesa(ArrayList<Carta> mesa) {
        this.mesa = mesa;
    }

    public void setCaidas(ArrayList<Carta> caidas) {
        this.caidas = caidas;
    }

    public void setMano(ArrayList<Carta> mano) {
        this.mano = mano;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
    
    public Tipo nuevoTipo(Tipo t){
      if(t==Tipo.Max){
        return Tipo.Min;
        }else{
      return Tipo.Max;
      }
      
    }
    public ArrayList<GameState> getFutureWithCarta(Carta c) {
            // System.out.println("\nmano: "+this.getMano());
             //   System.out.println(" mesa"+this.getMesa());
             //System.out.println("\nmano: "+this.tipo);
        
        ArrayList<GameState> futuros=new ArrayList<GameState>();
        
        int v=this.getValoracionCruda();
        if(v<this.alpha || v>this.beta) {
            System.out.println("Corto "+this.alpha+" "+this.beta+" "+v+" ");
            return  futuros;
        }
        
          
        
        
        //se puede llevar carta
        if(hayEstaCarta(c)){
            ArrayList<Carta> mesa3=(ArrayList<Carta>) this.getMesa().clone();
            ArrayList<Carta> mano3=(ArrayList<Carta>) this.getMano().clone();
            ArrayList<Carta> mano_probable3=(ArrayList<Carta>) this.getManoActual().clone();
                  Estado e3;
            e3 = (Estado) this.getEstado().clone();
            ArrayList<Carta> caidas3=(ArrayList<Carta>) this.getCaidas().clone();
            
            marcaParaLLevar(c, mesa3);
            
            simulaMovimiento(c, mesa3, mano_probable3, caidas3, e3, this.tipo);
           // carton_propio+=2;

            GameState llevar_state=new GameState(mesa3, caidas3, actualizaMano(mano_probable3, c, mano3),e3, c,nuevoTipo(this.tipo));
            llevar_state.jugada=Carta.Jugada.LLEVA;
            llevar_state.cloneAlphaBetaInicial(this.alpha, this.beta);
            futuros.add(llevar_state);
        }else{
        
        //lanzar carta
        ArrayList<Carta> mesa2=(ArrayList<Carta>) this.getMesa().clone();
       ArrayList<Carta> mano_probable2=(ArrayList<Carta>) this.getManoActual().clone();
        ArrayList<Carta> mano2=(ArrayList<Carta>) this.getMano().clone();
        
        ArrayList<Carta> caidas2=(ArrayList<Carta>) this.getCaidas().clone();
        caidas2.add(c);
        mesa2.add(c);
            Estado e2;
            e2 = (Estado) this.getEstado().clone();
        
        mano_probable2.remove(c);
        
       GameState lanzar=new GameState(mesa2, caidas2, actualizaMano(mano_probable2, c, mano2), e2, c, nuevoTipo(this.tipo));
   // GameState lanzar=(GameState) this.clone();
         lanzar.tipo=nuevoTipo(this.tipo);
         lanzar.jugada=Carta.Jugada.TIRA;
         lanzar.cloneAlphaBetaInicial(this.alpha, this.beta);
            
         futuros.add(lanzar);
         
         
        }
        if(hayParaLlevar(c, (ArrayList<Carta>) this.mesa.clone())){
                  ArrayList<Carta> mesa4=(ArrayList<Carta>) this.getMesa().clone();
            ArrayList<Carta> mano4=(ArrayList<Carta>) this.getMano().clone();
            ArrayList<Carta> mano_probable4=(ArrayList<Carta>) this.getManoActual().clone();
                  Estado e4;
            e4 = (Estado) this.getEstado().clone();
            ArrayList<Carta> caidas4=(ArrayList<Carta>) this.getCaidas().clone();
            
            marcaParaLlevarSuma(c, mesa4);
                    simulaMovimiento(c, mesa4, mano_probable4, caidas4, e4, this.tipo);
            GameState llevarsuma_state=new GameState(mesa4, caidas4, actualizaMano(mano_probable4, c, mano4),e4, c,nuevoTipo(this.tipo));
            llevarsuma_state.jugada=Carta.Jugada.LLEVA_SUMA;
            llevarsuma_state.cloneAlphaBetaInicial(this.alpha, this.beta);
            
            futuros.add(llevarsuma_state);
      
        }
        //GameState futuro=new GameState(mesa, caidas, mano, puntos_propios, puntos_rival, carton_propio, carton_rival, null);
        
    
    return futuros;
    }
    
    
    private ArrayList<Integer> sumasPosibles(ArrayList<Carta> mesa ){
   
    if(!mesa.isEmpty()){
       
    ArrayList <Carta> mesaPrima;
        mesaPrima=(ArrayList<Carta>) mesa.clone();
        ArrayList<Integer> sumas=new ArrayList();
        
        //int sumas[]=new int[mesaPrima.size()];
                   
        int s, i=0;
        while(!mesaPrima.isEmpty()){
            
            
            Carta c1=mesaPrima.get(0);
            for(Carta c2: mesaPrima){
            if(!c1.equals(c2)){
            s=c1.getNumero()+c2.getNumero();
                if(s<=7){
                    Integer k=new Integer(s);
                    sumas.add(k);
                    i++;
            
            }
            }
            
            }
        mesaPrima.remove(c1);
            
        }
        
        // System.out.println(" "+sumas+" ");
        return sumas;
        }else return null;
    }
    
    private boolean hayEstaCarta(Carta c){
    for(Carta x: this.getMesa()){
    if(c.compareTo(x)==0) return true;
    
    }
        return false;
    }
    
    
    private boolean hayParaLlevar(Carta c, ArrayList<Carta> mesa){
        ArrayList<Integer> sumas=sumasPosibles(mesa);
        if(sumas==null) return false;
        for(Integer i:sumas){
            if(i==c.getNumero())
                return true;
           
        }
        return false;
    }
    
        private int cuantasMasHay(int num, ArrayList <Carta> mesaAct){
        int hayMas=0;
        int x=num+1;
        boolean hay=true;
        while(hay){
            hay=false;
        for(Carta c: mesaAct){
            if(c.getNumero()==x){
            
            hayMas++;
            x++;
            hay=true;
            
            }
            
        }
        
        }
        return hayMas;
    }

    private void actualizaBase(){
        int k;
        /*
        for(int i=0; i<this.base.length; i++){
        this.base[i]=0;
        }
            for(Carta c: this.mano){
                k=c.getNumero();
                this.base[k]++;

        }
        for(Carta c: this.getCaidas()){
            k=c.getNumero();
                this.base[k]++;
               // System.out.println(base[k]);
        }
                */
        
        this.base_db=new ArrayList<DBUnit>();
        for(int i=1; i<11; i++){
        this.base_db.add(new DBUnit(i, 0));
        }
            for(Carta c: this.mano){
                k=c.getNumero();
                this.base_db.get(k-1).aumentar();

        }
        for(Carta c: this.getCaidas()){
            k=c.getNumero();
    this.base_db.get(k-1).aumentar();
 // System.out.println(base[k]);
        }
    }
    
  
    
    private void marcaCuantasMasHay(int num, ArrayList <Carta> mesa){
        
        int x=num+1;
        boolean hay=true;
        while(hay){
            hay=false;
        for(Carta c: mesa){
            if(c.getNumero()==x){
            
            c.select=true;
            x++;
            hay=true;
            
            }
            
        }
        
        }
       
    }
    
    public void marcaParaLLevar(Carta c){
        ArrayList <Carta> mesa=this.getMesa();

    marcaCuantasMasHay(c.getNumero()-1, mesa);    
        
    }
    
    public void marcaParaLLevar(Carta c,ArrayList <Carta> mesa ){
      //  ArrayList <Carta> mesa=this.getMesa();
        
    marcaCuantasMasHay(c.getNumero()-1, mesa);    
        
    }
    
    public void marcaParaLlevarSuma(Carta c, ArrayList <Carta> mesa){
         //  ArrayList <Carta> mesa=this.getMesa();
       ArrayList <Carta> marcadas=existeSuma(c.getNumero(), mesa);
       for(Carta x: marcadas){
       x.select=true;
       }
       marcaCuantasMasHay(c.getNumero(), mesa);
    
    }
    public ArrayList <Carta> existeSuma(int suma, ArrayList <Carta> mesa){
        ArrayList <Carta> marcadas=new ArrayList<Carta>();
        if(suma>7) return marcadas;
        
       for(Carta c1: mesa){
           for(Carta c2: mesa){
                if(!c2.equals(c1)&& (c1.getNumero()+c2.getNumero())==suma){
                    marcadas.add(c1);
                    marcadas.add(c2);
                    return marcadas;
             }
           }
       }
       return marcadas;
    
            
    }
    
    public ArrayList<Carta> buscaSuma(){
    
        ArrayList <Carta> cartaYmarcadas=new ArrayList();
        ArrayList <Carta> marcadas;
    for(Carta c: getMano()){
        if(c.getNumero()<=7){
              marcadas=existeSuma(c.getNumero(), mesa);
        if(!marcadas.isEmpty()){
        cartaYmarcadas.add(c);
        cartaYmarcadas.addAll(marcadas);
        
        }
            
            
            }
    }
    return cartaYmarcadas;
    
    }
    
    
    
      public int simulaMovimiento(Carta c, ArrayList<Carta> mesa,ArrayList<Carta> mano, ArrayList<Carta> caidas, Estado estado, Tipo tipo)
      {
          int valoracion=0;
            int suma=0;
        ArrayList <Carta> marcadas=new ArrayList();

        for(Carta k: mesa){
            if(k.select){
            suma=suma+k.getNumero();
            marcadas.add(k);
            }
    }
        
        
        if(suma==0){  
       
            
       mesa.add(c);//añade carta a la mesa
       
       caidas.add(c); //añade carta a caidas
       
       mano.remove(c);//saca la carta de la mano del jugador
       
        //this.setUltima(c);//ultima carta
       
       return valoracion;
        }
        
      
        if(this.isEscalera(marcadas, c)){
         actualizaPuntos(c, marcadas, estado, true, tipo);
           estado.actualizaCarton(tipo, marcadas.size()+1);
        
            for(Carta m: marcadas){
            mesa.remove(m);
                //jug.getLlevadas().add(m); hay que sumar al carton
           
               caidas.add(m);
            
            m.select=!m.select;
   
            }
        mano.remove(c); //saca de la man0
        caidas.add(c);//añande carta a caidas
        
        
        //jug.getLlevadas().add(c);// la pone en llevadas, suma a carton
       // jug.setUltima(c);//ultima carta
        
        
        
       
        return valoracion;

        }    

        if(marcadas.size()>1&&isEscaleraSuma(marcadas, c) ){
        
            actualizaPuntos(c, marcadas, estado, false, tipo);
            estado.actualizaCarton(tipo, marcadas.size()+1);
      
            
            for(Carta m: marcadas){
            mesa.remove(m);
           
             caidas.add(m);//añade carta a caidas
           // jug.getLlevadas().add(m);
            
            m.select=!m.select;
    
            }
     
        mano.remove(c);
        caidas.add(c);//añade carta a caidas
        //jug.getLlevadas().add(c); carton++
        //jug.setUltima(c);

      
        
        return valoracion;
        }else{
        return valoracion;
        }

    }


      
    public boolean isEscalera(ArrayList <Carta> marcadas, Carta carta){
   ArrayList <Carta> m2=marcadas;
    //    System.out.println(marcadas);
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
    private ArrayList<Carta> actualizaMano(ArrayList<Carta> mano_actual, Carta c,ArrayList<Carta> mano ){
        mano_actual.remove(c);
        if (this.isMax()){
            mano.remove(c);
            return mano;
        }
        else {
        return mano;
        }
                
    }
    
    public boolean isMax(){
        return this.tipo==Tipo.Max;
        }
    
    private Juego.Situacion ObtieneSituacion(Carta tirada, ArrayList<Carta> marcadas, boolean isEscalera){
    int indicador;
    Juego.Situacion s;
    if(isCaida(marcadas.get(0)) && isEscalera){
    s= Juego.Situacion.Caida;
//        if(isCaidaEnRonda(getOponente(j), marcadas.get(0))){
//            s= Juego.Situacion.CaidaEnRonda;
//            if(seraLimpia(marcadas)){
//                s=Juego.Situacion.CaidaYLimpiaEnRonda;
//                }
//        }
        if(seraLimpia(marcadas)){
        s=Juego.Situacion.CaidaYLimpia;
        }
        return s;
    }
    if(seraLimpia(marcadas)){
    s=Juego.Situacion.Limpia;
    return s;
    }
   return Juego.Situacion.Nada;
    

}
    
    public void actualizaPuntos(Carta tirada, ArrayList<Carta> marcadas, Estado e, boolean escalera, Tipo tipo){
    Juego.Situacion s=ObtieneSituacion(tirada, marcadas, escalera);
    if(!e.esta38(tipo)){
        
    switch(s){
        case Caida:
            //muestra mensaje
        e.actualizaPoints(tipo,2);  
            break;
        case CaidaEnRonda:   
            //muestra mensaje
                    e.actualizaPoints(tipo,4);
                break;
        case CaidaYLimpia:
                    e.actualizaPoints(tipo,4);
                 break;
         case  CaidaYLimpiaEnRonda:
        
                        e.actualizaPoints(tipo,6);
         break;
         case Limpia:
             
                        e.actualizaPoints(tipo,2);
        break;
             
    }
    }
    else{
     switch(s){
    case Caida:
         
                   e.actualizaPoints(tipo,2);
             break;
    }
    }
    
        }

   private boolean isLimpia(){
   if(  this.getMesa().isEmpty()) return true;
   else return false;
       
   }
   private boolean seraLimpia(ArrayList <Carta> marcadas){
   if(getMesa().size()==marcadas.size()) return true;
   return false;
   }
   
    private boolean isCaida( Carta marcada){
    if(this.cartaJugada!=null){
        if(cartaJugada.equals(marcada)){
  
            return true;
    
    }
    }
    return false;
    }
public int getValoracionCruda(){

    int v;
    Estado e=this.getEstado();
    v=e.carton_propio+e.puntos_propios-e.carton_rival-e.puntos_rival;
     //v=e.puntos_propios-e.puntos_rival;
    v=2*v;
    if(this.estado.carton_propio>=20) v+=10;
    if(this.estado.carton_rival>=20) v-=10;
    if(this.estado.puntos_propios>=40) v+=100;
    if(this.estado.puntos_rival >=40) v-=100;
   

    this.valoracion=v;
    return v;
}
    
    
    
    
    public class DBUnit implements Comparable<DBUnit>{
        int num_carta;
        int cuantas;

        public DBUnit(int num_carta, int cuantas) {
            this.num_carta = num_carta;
            this.cuantas = cuantas;
        }

        @Override
        public String toString() {
            return "#"+this.num_carta +" "+this.cuantas; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int compareTo(DBUnit o) {
          if(this.cuantas<o.cuantas){
        return -1;
        }else if(this.cuantas==o.cuantas) return 0;
       else if(this.cuantas>o.cuantas) return 1;
        return 0;
        }
        
        public void aumentar(){
        this.cuantas++;
        }
        
        
    }
    
    public class Estado implements Cloneable{
    int puntos_propios;
    int puntos_rival;
    int carton_propio;
    int carton_rival;
    
        public Estado() {
            
        }

       
    @Override
        public Object clone()  {
             try {
            Estado v = (Estado) super.clone();
          
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        }

        public Estado(int puntos_propios, int puntos_rival, int carton_propio, int carton_rival) {
            this.puntos_propios = puntos_propios;
            this.puntos_rival = puntos_rival;
            this.carton_propio = carton_propio;
            this.carton_rival = carton_rival;
        }

        public int getPuntos_propios() {
            return puntos_propios;
        }

        public void setPuntos_propios(int puntos_propios) {
            this.puntos_propios = puntos_propios;
        }

        public int getPuntos_rival() {
            return puntos_rival;
        }

        public void setPuntos_rival(int puntos_rival) {
            this.puntos_rival = puntos_rival;
        }

        public int getCarton_propio() {
            return carton_propio;
        }

        public void setCarton_propio(int carton_propio) {
            this.carton_propio = carton_propio;
        }

        public int getCarton_rival() {
            return carton_rival;
        }

        public void setCarton_rival(int carton_rival) {
            this.carton_rival = carton_rival;
        }
    
        public void actualizaPoints(Tipo t, int dif){
        if(t==Tipo.Max){
        this.puntos_propios+=dif;
        }else{
        this.puntos_rival+=dif;
        
        }
        }
        
        
        public void actualizaCarton(Tipo t, int dif){
        if(t==Tipo.Max){
        this.carton_propio+=dif;
        }else{
        this.carton_rival+=dif;
        
        }
        }
        public boolean esta38(Tipo t){
        if(t==Tipo.Max){
        return this.puntos_propios==38;
        }else{
        return this.puntos_rival==38;
        
        }
        }
    
}
}
