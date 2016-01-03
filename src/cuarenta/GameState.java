/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Usuario
 */
public class GameState {
    private ArrayList <Carta> mesa;
    private ArrayList <Carta> caidas;
    int[] base=new int[11];
    private ArrayList <Carta> mano;
    int puntos_propios;
    int puntos_rival;
    int carton_propio;
    int carton_rival;
    int valoracion;
    Carta cartaJugada;
    Carta mejorJugada;
        public enum Tipo{Min, Max}
    Tipo tipo;
    private ArrayList <Carta> mano_probable;
    
    
    public GameState(ArrayList<Carta> mesa, ArrayList<Carta> caidas, ArrayList<Carta> mano, int puntos_propios, int puntos_rival, int carton_propio, int carton_rival,Carta cartaJugada, Tipo t) {
        this.mesa = mesa;
        this.caidas = caidas;
        this.mano = mano;
        this.puntos_propios = puntos_propios;
        this.puntos_rival = puntos_rival;
        this.carton_propio = carton_propio;
        this.carton_rival = carton_rival;
        this.valoracion=-100;
        this.cartaJugada=cartaJugada;
        this.mejorJugada=null;
        this.tipo=t;
        
        if(t==Tipo.Min){
            mano_probable=new ArrayList <Carta>();
        actualizaBase();
        for (int i=0;i<base.length;i++){
        base[i]=0;
        }
       
        for (int i=1;i<5;i++){
            if(base[i]<1){
                Carta c=new Carta(i, 1);
                mano_probable.add(c);
            }
         }
       
             //   Carta c=new Carta(5, 1);
               // mano_probable.add(c);
        
                //Carta c2=new Carta(6, 1);
                //mano_probable.add(c2);
        }
    }

    private GameState(ArrayList<Carta> mesa, ArrayList<Carta> caidas, ArrayList<Carta> mano, int puntos_propios, int puntos_rival, int carton_propio, int carton_rival) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public ArrayList<GameState> getFutureWithCarta(Carta c){
            // System.out.println("\nmano: "+this.getMano());
             //   System.out.println(" mesa"+this.getMesa());
             //System.out.println("\nmano: "+this.tipo);
                
        ArrayList<GameState> futuros=new ArrayList<GameState>();
               
        
        
        //se puede llevar carta
        if(hayEstaCarta(c)){
            ArrayList<Carta> mesa3=(ArrayList<Carta>) this.getMesa().clone();
            ArrayList<Carta> mano3=(ArrayList<Carta>) this.getMano().clone();
            ArrayList<Carta> mano_probable3=(ArrayList<Carta>) this.getManoActual().clone();
            
            ArrayList<Carta> caidas3=(ArrayList<Carta>) this.getCaidas().clone();
            
            marcaParaLLevar(c, mesa3);
            
            simulaMovimiento(c, mesa3, mano_probable3, caidas3);
            carton_propio+=2;
            
            GameState llevar_state=new GameState(mesa3, caidas3, actualizaMano(mano_probable3, c, mano3), puntos_propios, puntos_rival, carton_propio, carton_rival, null,nuevoTipo(this.tipo));
            futuros.add(llevar_state);
        }else{
        
        //lanzar carta
        ArrayList<Carta> mesa2=(ArrayList<Carta>) this.getMesa().clone();
       ArrayList<Carta> mano_probable2=(ArrayList<Carta>) this.getManoActual().clone();
        ArrayList<Carta> mano2=(ArrayList<Carta>) this.getMano().clone();
        
        ArrayList<Carta> caidas2=(ArrayList<Carta>) this.getCaidas().clone();
        caidas2.add(c);
        mesa2.add(c);
        
        mano_probable2.remove(c);
        
        GameState lanzar=new GameState(mesa2, caidas2, actualizaMano(mano_probable2, c, mano2), puntos_propios, puntos_rival, carton_propio, carton_rival, null, nuevoTipo(this.tipo));
        futuros.add(lanzar);
       
        
        }
        //GameState futuro=new GameState(mesa, caidas, mano, puntos_propios, puntos_rival, carton_propio, carton_rival, null);
        
    
    
    return futuros;
    }
   
    
      private ArrayList<Integer> sumasPosibles(){
   
    if(this.getMesa().isEmpty()){
       
    ArrayList <Carta> mesaPrima;
        mesaPrima=(ArrayList<Carta>) this.getMesa().clone();
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
        
         System.out.print(" "+sumas+" ");
        return sumas;
        }else return null;
    }
    
    private boolean hayEstaCarta(Carta c){
    for(Carta x: this.getMesa()){
    if(c.compareTo(x)==0) return true;
    
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
    
    for(int i=0; i<base.length; i++){
    base[i]=0;
    }
        for(Carta c: mano){
            k=c.getNumero();
            base[k]++;
    
    }
    for(Carta c: this.getCaidas()){
        k=c.getNumero();
            base[k]++;
    
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
    
    public void marcaParaLlevarSuma(Carta c){
           ArrayList <Carta> mesa=this.getMesa();
       ArrayList <Carta> marcadas=existeSuma(c.getNumero());
       for(Carta x: marcadas){
       x.select=true;
       }
       marcaCuantasMasHay(c.getNumero(), mesa);
    
    }
    public ArrayList <Carta> existeSuma(int suma){
        ArrayList <Carta> marcadas=new ArrayList<Carta>();
        if(suma>7) return marcadas;
        
       for(Carta c1: this.getMesa()){
           for(Carta c2: this.getMesa()){
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
              marcadas=existeSuma(c.getNumero());
        if(!marcadas.isEmpty()){
        cartaYmarcadas.add(c);
        cartaYmarcadas.addAll(marcadas);
        
        }
            
            
            }
    }
    return cartaYmarcadas;
    
    }
    
    
    
      public int simulaMovimiento(Carta c, ArrayList<Carta> mesa,ArrayList<Carta> mano, ArrayList<Carta> caidas)
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
       
       
       
            //return true;
            return valoracion;
        }
        
      
        if(this.isEscalera(marcadas, c)){
//            actualizaPuntos(c, marcadas, jug, true);
            
            for(Carta m: marcadas){
            mesa.remove(m);
                //jug.getLlevadas().add(m); hay que sumar al carton
           
            // caidas.add(m);
            
            m.select=!m.select;
   
            }
        mano.remove(c); //saca de la man0
        caidas.add(c);//añande carta a caidas
        
        //jug.getLlevadas().add(c);// la pone en llevadas, suma a carton
       // jug.setUltima(c);//ultima carta
        
        
        
       
        return valoracion;

        }    

        if(marcadas.size()>1&&isEscaleraSuma(marcadas, c) ){
        
            
          //  actualizaPuntos(c, marcadas, jug, false);
            
            for(Carta m: marcadas){
           mesa.remove(m);
           // caidas.add(m);//añade carta a caidas
           // jug.getLlevadas().add(m);
            
           // m.select=!m.select;
    
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
    
    public void actualizaPuntos(Carta tirada, ArrayList<Carta> marcadas, Jugador j, boolean escalera){
    Juego.Situacion s=ObtieneSituacion(tirada, marcadas, escalera);
    if(!j.esta38){
        
    switch(s){
        case Caida:
            //muestra mensaje
         j.setPuntos(j.getPuntos()+2);  
            break;
        case CaidaEnRonda:   
            //muestra mensaje
            
            j.setPuntos(j.getPuntos()+4);   
            break;
        case CaidaYLimpia:
             
            j.setPuntos(j.getPuntos()+4);   
            break;
         case  CaidaYLimpiaEnRonda:
        
             j.setPuntos(j.getPuntos()+6); 
             break;
         case Limpia:
             
             j.setPuntos(j.getPuntos()+2); 
            break;
             
    }
    }
    else{
     switch(s){
    case Caida:
         
        j.setPuntos(j.getPuntos()+2); 
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


}
