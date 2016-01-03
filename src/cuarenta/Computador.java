/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import cuarenta.GameState.Tipo;
import java.util.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Mtech
 */
public class Computador extends Jugador implements Observer {
 FrMesa fr;
 int[] base=new int[11];
 TreeNode <Carta> gameTree;

    protected FrMesa getFr() {
        return fr;
    }

    
    public void setFr(FrMesa fr) {
        this.fr = fr;
    }
    
//
//   public ArrayList<Carta> buscaLlevar(ArrayList <Carta> mesa){
//   ArrayList <Carta>cartaYmarcadas=new ArrayList();
//       for(Carta c: mesa){
//           for(Carta c2: getMano()){
//           if(c.compareTo(c2)==0){
//               cartaYmarcadas.add(c2);
//               cartaYmarcadas.add(c);
//              return cartaYmarcadas; 
//             }
//            }
//       }
//       return cartaYmarcadas;
//   }
//   
//   public ArrayList<Carta> buscaSumaMaxima(ArrayList <Carta> mesa){
//   ArrayList <Carta> cartaYmarcadas=new ArrayList();
//   ArrayList <Carta> ord=mesa;
//     ArrayList <Carta> ord2=mesa;
//   Collections.sort(ord);
//   int sumaM=0;
//   
//   int sumas[]=new int[45];
//   int i=0;
//       for(Carta c: ord){
//           for(Carta c2: ord2){
//           if(c.getNumero()<=c2.getNumero() && !c2.equals(c)&& (c.getNumero()+c2.getNumero())<=7){
//              sumas[i]=c.getNumero()+c2.getNumero();
//              i++;
//             }
//          
//            }
//            
//       }
//       for(int j=0; j<sumas.length; j++){
//       System.out.print(sumas[j]+" ");}
//        System.out.print("\n");
//       return cartaYmarcadas;
//   }
//      
   
    public Carta botaAlAzar(){
    int k=(int) (Math.random()*this.getMano().size()-1);
    return this.getMano().get(k);
    
    }
    
//    public ArrayList <Carta> buscaCaer(){
//    ArrayList <Carta> cartaYmarcadas=new ArrayList();
//    Carta ultima=this.getJuego().getOponente(this).getUltima();
//    for(Carta c: getMano()){
//    if(ultima.compareTo(c)==0)
//    {
//        if(this.getJuego().getMesa().contains(ultima)){
//       cartaYmarcadas.add(c);
//     cartaYmarcadas.add(ultima);
//    }
//    
//    }
//    }
//    return cartaYmarcadas;
//    }
    
    public ArrayList <Carta> existeSuma(int suma){
        ArrayList <Carta> marcadas=new ArrayList<Carta>();
        if(suma>7) return marcadas;
        
       for(Carta c1: getJuego().getMesa()){
           for(Carta c2: getJuego().getMesa()){
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
    
    
    @Override
    public void update(Observable o, Object arg) {
       
        
        //fr.juego.movimientoPc(fr);
        if(getJuego().nivel==1){
        movimientoPcNv1();
        }else if(getJuego().nivel>=2){
          movimientoPcNivel2();   
        }
               
    }
    
    private boolean hayCaida(Carta c){
    
    //ArrayList <Carta> cartaYmarcadas=new ArrayList();
    Carta ultima=this.getJuego().getOponente(this).getUltima();
        
    if(ultima.compareTo(c)==0)
    {
        if(this.getJuego().getMesa().contains(ultima)){
     //  cartaYmarcadas.add(c);
     //cartaYmarcadas.add(ultima);
            return true;
    }
    
    }
       
        
    return false;
    }
    
    private ArrayList<Integer> sumasPosibles(){
   
    if(!getJuego().getMesa().isEmpty()){
       
    ArrayList <Carta> mesaPrima;
        mesaPrima=(ArrayList<Carta>) getJuego().getMesa().clone();
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
    
    
    private int maximollevadas(Carta c, ArrayList <Integer> sumasPosib){
       
            
        int maxLLevadas=0;
        //mira si hay una suma
        if(sumasPosib!=null){
        for(Integer i:sumasPosib){
        if(c.getNumero()==i.intValue()){
            maxLLevadas=2;
            c.setJugada(Carta.Jugada.LLEVA_SUMA);
        }
        }
        }
        //mira si hay una carta
        
        if(maxLLevadas==0){
        
            if(hayEstaCarta(c)){
                maxLLevadas=1;
                c.setJugada(Carta.Jugada.LLEVA);
            }
        }
        //si lleva mira cuantas mas puede llevar
        if(maxLLevadas>0){
        int hayMas=cuantasMasHay(c.getNumero(), getJuego().getMesa());
        maxLLevadas=maxLLevadas+hayMas;
        
        
        }
        return maxLLevadas;
        
    }
    /**
     * indica cuantas cartas mas puede llevar una carta de la mano del pc
     * @param num
     * @param mesaAct
     * @return 
     */
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
    
    private boolean seraLimpia(int llevadas){
    
        if(llevadas!=0 && llevadas==getJuego().getMesa().size()){
        return true;
        }
        else return false;
        
    }
    private boolean hayEstaCarta(Carta c){
    for(Carta x: getJuego().getMesa()){
    if(c.compareTo(x)==0) return true;
    
    }
        return false;
    }
    
    
    private void colocaGanancia(Carta c, ArrayList <Integer> sumasPosib){
    int ganancia;
    int ganTmp;
        
    ganancia=maximollevadas(c, sumasPosib);
    if(seraLimpia(ganancia)){
    ganancia=10+ganancia;
    }
    
        if(hayCaida(c)){
            ganTmp=10+1+cuantasMasHay(c.getNumero(), getJuego().getMesa());
            if(ganTmp>ganancia){
            ganancia=ganTmp;
                c.setJugada(Carta.Jugada.LLEVA);
        }
        }
        
        
        if(ganancia==0){
        c.setJugada(Carta.Jugada.TIRA);
        }
        //si la ganancia es mayor que cero
        c.setGanancia(ganancia);
        //si es menor que cero..
        
    }
    
    
    public void colocaGanancia(){
        ArrayList <Integer> sumasPos=sumasPosibles();
    for (Carta c: getMano()){
        colocaGanancia(c, sumasPos);
        
    }
    
    }
    
    
    public void imprimeGanancias(){
        System.out.println("\nGanancias: ");
    for(Carta c: getMano()){
        System.out.print(c.getGanancia()+" ");
    }
    System.out.println("\nmano: "+getMano()+"\n");
    System.out.println("mesa"+getJuego().getMesa()+"\n");
           
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
        ArrayList <Carta> mesa=getJuego().getMesa();

    marcaCuantasMasHay(c.getNumero()-1, mesa);    
        
    }
    
    public void marcaParaLlevarSuma(Carta c){
           ArrayList <Carta> mesa=getJuego().getMesa();
       ArrayList <Carta> marcadas=existeSuma(c.getNumero());
       for(Carta x: marcadas){
       x.select=true;
       }
       marcaCuantasMasHay(c.getNumero(), mesa);
    
    }
    public Carta obtieneCartaMayorGanancia(){
    return Collections.max(getMano(), new comparaPorGanancia());
       
    }
    
   
    public void movimientoPcNv1(){
    colocaGanancia();
    imprimeGanancias();
    
    Carta c=obtieneCartaMayorGanancia();
    System.out.print(c+" "+c.getJugada());
    
    if(c.getGanancia()>0){
        if(c.getJugada()==Carta.Jugada.LLEVA){
        marcaParaLLevar(c);
        }
        else if(c.getJugada()==Carta.Jugada.LLEVA_SUMA){
        marcaParaLlevarSuma(c);
        }
        
                    c.getCb().arriba=true;
                   Timer t=new Timer();
                   
                   TimerTask mover= new MueveLuego(fr, c);
                   fr.habilitar(false);
                   t.schedule(mover, 2000);
    
    }else {
    c= botaAlAzar();
      c.getCb().arriba=true;
    Timer t=new Timer();
                   
                   TimerTask mover= new MueveLuego(fr, c);
                   
                  // fr.setEnabled(false);
                   fr.habilitar(false);
                   t.schedule(mover, 2000);
    }
    
    }
    
    public void movimientoPcNivel2(){
    //arbol
        construyeArbolDeJuego();
        
    colocaGanancia();
    imprimeGanancias();
    
    Carta c=obtieneCartaMayorGanancia();
    System.out.print(c+" "+c.getJugada());
      System.out.print(" caidas:"+getJuego().getCaidas().size());
    if(c.getGanancia()>0){
        if(c.getJugada()==Carta.Jugada.LLEVA){
        marcaParaLLevar(c);
        }
        else if(c.getJugada()==Carta.Jugada.LLEVA_SUMA){
        marcaParaLlevarSuma(c);
        }
        
                    c.getCb().arriba=true;
                   Timer t=new Timer();
                   
                   TimerTask mover= new MueveLuego(fr, c);
                   
                   fr.habilitar(false);
                   
                   t.schedule(mover, 2000);
    
    }else {
        actualizaBase();
        colocaGananciaNegativa();
        c=obtieneCartaMayorGanancia();
        
    imprimeGanancias();
     System.out.print(c+" "+c.getJugada());
    
    
      c.getCb().arriba=true;
      //
      fr.muestraMensaje(c.getNumero());
    //
      
      Timer t=new Timer();
                   
                   TimerTask mover= new MueveLuego(fr, c);
             
                   fr.habilitar(false);
                   t.schedule(mover, 2000);
    }
    
    }
    
    public void actualizaBase(){
    int k;
    
    for(int i=0; i<base.length; i++){
    base[i]=0;
    }
        for(Carta c: getMano()){
            k=c.getNumero();
            base[k]++;
    
    }
    for(Carta c: getJuego().getCaidas()){
        k=c.getNumero();
            base[k]++;
    
    }
    
    }
    private void colocaGananciaNegativa(){
    for(Carta c: getMano()){
        c.setGanancia(calculaProbabilidadSerCaida(c)*-1);
        
    }
    }
    
    private int calculaProbabilidadSerCaida(Carta c){
        int k=c.getNumero();
        int frecuencia=base[k];
        return 100-(frecuencia*100)/4;
    }
    
    //************** aqui vamos a implementar el arbol de juego************///
    private void construyeArbolDeJuego(){
        TreeNode <GameState> gameTree=new TreeNode<GameState>(null);
        GameState juegoActual=new GameState(this.getJuego().getMesa(), this.getJuego().getCaidas(), this.getMano(),this.getPuntos(),this.getJuego().getJugador().getPuntos(), this.getLlevadas().size(), this.getJuego().getJugador().getLlevadas().size(), null, Tipo.Max);
      gameTree.data=juegoActual;  
        
        seteaHijos(gameTree);
        printTree(gameTree);
    }
    private void seteaHijos(TreeNode<GameState> n){
         
        GameState game_act=n.data;
         ArrayList <Carta> mano_copy=(ArrayList <Carta>) game_act.getManoActual();
        System.out.println(mano_copy.size());
        // if( mano_copy.isEmpty()) return;//caso base
         if( game_act.getMano().isEmpty()) return;//caso base
        
        
        for(Carta c: mano_copy){
        ArrayList <GameState> hijos_data= game_act.getFutureWithCarta(c);
            System.out.println("hijos :"+hijos_data.size());
        for(GameState g: hijos_data){
            n.addChild(g);
                }
        }
        for(TreeNode h: n.children){
            seteaHijos(h);
        }
        
    }
    
    public void printTree(TreeNode<GameState>  tmpRoot) {

        Queue<TreeNode> currentLevel = new LinkedList<TreeNode>();
        Queue<TreeNode> nextLevel = new LinkedList<TreeNode>();

        currentLevel.add(tmpRoot);
        int nivel=0;
        
        while (!currentLevel.isEmpty()) {
            System.out.println("Nivel "+nivel);
            Iterator<TreeNode> iter = currentLevel.iterator();
            while (iter.hasNext()) {
                TreeNode<GameState> currentNode = iter.next();
                for(TreeNode h: currentNode.children){
                nextLevel.add(h);
                }
                 System.out.println("\nmano: "+currentNode.data.getMano());
                System.out.println(" mesa"+currentNode.data.getMesa());
                System.out.println(" puntos: "+currentNode.data.getEstado().puntos_propios+" "+currentNode.data.getEstado().puntos_rival);
            }
            System.out.println();
            currentLevel = nextLevel;
            nextLevel = new LinkedList<TreeNode>();
            nivel++;
        }

    }
    private void imprimirGameTree(TreeNode<GameState> n){
         GameState game_act=n.data;
        
         
         
               
    }
    /***** arbol de juego  end ****************/
    
    
    //************* esta es una clase aparte ára que mueva luego **********/
     public class MueveLuego extends TimerTask {
     FrMesa fr;
        Carta c2;
        public MueveLuego( FrMesa fr, Carta c2) {
        this.fr=fr;
        this.c2=c2;
        }
       
        @Override
        public void run() {

            fr.juego.movimiento(c2, fr, fr.juego.getPc());
       
           
            fr.actualizaPuntosLabel(fr.juego.getPc());
             fr.actualizaCartonLabel(fr.juego.getPc());
             
             if(fr.juego.hayGanador()==2){  
                    JOptionPane.showMessageDialog(null, "Gano PC");
                    if(getJuego().carrera) fr.reiniciarJuegoCarrera();
                   else fr.suspenderJuego();
                                  
                    }else if(getJuego()!=null){
            fr.juego.repartir(fr);}
             
         fr.habilitar(true);
        }
    }

}