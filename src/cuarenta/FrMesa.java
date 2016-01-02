/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Mtech
 */

public class FrMesa extends javax.swing.JFrame {
    Juego juego;
    ArrayList <CartaButton> manoJ1;
    ArrayList <CartaButton> manoPC;
    ArrayList <CartaButton> mesa;
    boolean jugando;
    
    public Juego getMesa() {
        return juego;
    }

    public void setMesa(Juego juego) {
        this.juego = juego;
    }

   
    /**
     * Creates new form FrMesa
     */
    
    public FrMesa() {

        
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/cuarenta/Imagenes/iconodetras.png")).getImage());
        this.setTitle("Cuarenta v1.0");
        
        
        this.buttonGroup1.add(this.radioButtonNivelDOS);
        this.buttonGroup1.add(this.radioButtonNivelUNO);
       jugando=false;
      this.menuTerminarJuego.setEnabled(false);
   
           manoJ1=new ArrayList();
        manoPC=new ArrayList();
        mesa=new ArrayList();
   
      this.setCartasButton();  
      this.configuraTipo();
     
    }

   public void iniciarJuego(){
           juego=new Juego();
       juego.getPc().setFr(this);
        
       configuraNivel(false);
     
        this.inicializaCartasButton();
      
        jugando=true;
        this.menuJuegoNuevo.setEnabled(!jugando);
        this.menuModoCarrera.setEnabled(!jugando);
        this.menuTerminarJuego.setEnabled(jugando);
  
         juego.repartir(this);
   }
   
   
   public void iniciarJuego(boolean carrera, ModoCarrera mc){
           juego=new Juego(carrera);
       juego.getPc().setFr(this);
       
       if(carrera) juego.modoCarrera=mc;
        
       configuraNivel(carrera);     
       
       this.inicializaCartasButton();
      
        jugando=true;
        this.menuJuegoNuevo.setEnabled(!jugando);
         this.menuModoCarrera.setEnabled(!jugando);
        this.menuTerminarJuego.setEnabled(jugando);
  
         juego.repartir(this);
         
         if(carrera){
            JOptionPane.showMessageDialog(null, "Modo carrera\n Nivel: "+juego.nivel);
         }
   }
   
   public void suspenderJuego(){
      
        juego.culminaJuego();
      for(CartaButton c: this.manoJ1){
      c.carta=null;
      c.habilitada=true;
      }
      
      for(CartaButton c: this.manoPC){
      c.carta=null;
      }
      
      for(CartaButton c: this.mesa){
      c.carta=null;
      c.habilitada=true;
      }
   
      this.puntosLabel.setText("Puntos: 0");
      this.puntosPcLabel.setText("Puntos: 0"); 
      this.numLlevadasLabel.setText("Cartón: 0");
      this.numLlevadasPcLabel.setText("Cartón: 0");   
      
      
        jugando=false;
        this.menuJuegoNuevo.setEnabled(!jugando);
        this.menuModoCarrera.setEnabled(!jugando);
        this.menuTerminarJuego.setEnabled(jugando);
       
        juego=null;
}
   
   
   public void reiniciarJuegoCarrera(){
      
      juego.culminaJuego();
      for(CartaButton c: this.manoJ1){
      c.carta=null;
      c.habilitada=true;
      }
      
      for(CartaButton c: this.manoPC){
      c.carta=null;
      }
      
      for(CartaButton c: this.mesa){
      c.carta=null;
      c.habilitada=true;
      }
   
      this.puntosLabel.setText("Puntos: 0");
      this.puntosPcLabel.setText("Puntos: 0"); 
      this.numLlevadasLabel.setText("Cartón: 0");
      this.numLlevadasPcLabel.setText("Cartón: 0");   
      
      
        jugando=false;
        this.menuJuegoNuevo.setEnabled(!jugando);
        this.menuModoCarrera.setEnabled(!jugando);
        this.menuTerminarJuego.setEnabled(jugando);
        
        ModoCarrera mc=juego.modoCarrera;
               juego=null;
               iniciarJuego(true, mc);
                
        
}
   
    private void setCartasButton(){
        manoPC.add(cartaButton1);
        manoPC.add(cartaButton2);
        manoPC.add(cartaButton3);
        manoPC.add(cartaButton4);
        manoPC.add(cartaButton5);
       
        manoJ1.add(cartaButton6);
        manoJ1.add(cartaButton7);
        manoJ1.add(cartaButton8);
        manoJ1.add(cartaButton9);
        manoJ1.add(cartaButton10);
        
        mesa.add(cartaButton11);
        mesa.add(cartaButton12);
        mesa.add(cartaButton13);
        mesa.add(cartaButton14);
        mesa.add(cartaButton15);
        mesa.add(cartaButton16);
        mesa.add(cartaButton18);
        mesa.add(cartaButton17);
        mesa.add(cartaButton19);
        mesa.add(cartaButton20);
        
    }
    
private void configuraTipo(){
    for(CartaButton c: manoJ1){
    c.tipo=1;
      }
    for(CartaButton c: manoPC){
    c.tipo=2;
      }
    for(CartaButton c: mesa){
    c.tipo=0;
      }
    }
public void configuraNivel(boolean carrera){
    if(!carrera){
        if(this.radioButtonNivelUNO.isSelected()){
        juego.nivel=1;
        }else if(radioButtonNivelDOS.isSelected()){
        juego.nivel=2;
        }
    }else{
    juego.nivel=juego.modoCarrera.getNivel();
    }
        
}
public  void actualizaManosButton(){
   Jugador j=juego.getJugador();
   int i=0;
   for(Carta c: j.getMano() ){
       CartaButton cb;     
        cb=(CartaButton)this.manoJ1.get(i);
        cb.carta=c;
       c.setCb(cb);
           
            c.addObserver(cb);
            c.cambio();
            c.notifyObservers();
                i++;
            }
   
   
   Jugador j2=juego.getPc();
   int k=0;
   for(Carta c: j2.getMano() ){
     CartaButton cb;     
        cb=(CartaButton)this.manoPC.get(k);
        cb.carta=c;
       c.setCb(cb);
        
       c.addObserver(this.manoPC.get(k));
            c.cambio();
            c.notifyObservers();
            k++;
                

            }  
   
}

   public void inicializaCartasButton(){
  
       
       actualizaManosButton();
       
   int k2=0;
   for(Carta c: juego.getMesa() ){

        this.mesa.get(k2).carta=c;
         c.addObserver(this.mesa.get(k2));
            k2++;
    }
   
     }
    

public void actualizaCartonLabel(Jugador j){
    if(j.equals(juego.getJugador()))
         this.numLlevadasLabel.setText("Cartón: "+  juego.getJugador().getLlevadas().size());
    else if(j.equals(juego.getPc()))
         this.numLlevadasPcLabel.setText("Cartón: "+  juego.getPc().getLlevadas().size());
        

}

    public CartaButton buscaVacia(){
   
    for(CartaButton cb: mesa){
   if(cb.carta==null){
    return cb;
   }
    }
    return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        texturaPanel1 = new cuarenta.texturaPanel();
        jLabel1 = new javax.swing.JLabel();
        numLlevadasPcLabel = new javax.swing.JLabel();
        puntosPcLabel = new javax.swing.JLabel();
        cartaButton1 = new cuarenta.CartaButton();
        cartaButton2 = new cuarenta.CartaButton();
        cartaButton3 = new cuarenta.CartaButton();
        cartaButton4 = new cuarenta.CartaButton();
        cartaButton5 = new cuarenta.CartaButton();
        cartaButton11 = new cuarenta.CartaButton();
        cartaButton12 = new cuarenta.CartaButton();
        cartaButton13 = new cuarenta.CartaButton();
        cartaButton14 = new cuarenta.CartaButton();
        cartaButton15 = new cuarenta.CartaButton();
        cartaButton16 = new cuarenta.CartaButton();
        cartaButton17 = new cuarenta.CartaButton();
        cartaButton18 = new cuarenta.CartaButton();
        cartaButton19 = new cuarenta.CartaButton();
        cartaButton20 = new cuarenta.CartaButton();
        puntosLabel = new javax.swing.JLabel();
        numLlevadasLabel = new javax.swing.JLabel();
        cartaButton6 = new cuarenta.CartaButton();
        cartaButton7 = new cuarenta.CartaButton();
        cartaButton8 = new cuarenta.CartaButton();
        cartaButton9 = new cuarenta.CartaButton();
        cartaButton10 = new cuarenta.CartaButton();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuJuegoNuevo = new javax.swing.JMenuItem();
        menuModoCarrera = new javax.swing.JMenuItem();
        menuTerminarJuego = new javax.swing.JMenuItem();
        menuNivel = new javax.swing.JMenu();
        radioButtonNivelUNO = new javax.swing.JRadioButtonMenuItem();
        radioButtonNivelDOS = new javax.swing.JRadioButtonMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuInstrucciones = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        texturaPanel1.setBackground(new java.awt.Color(0, 204, 51));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Jugador 2");

        numLlevadasPcLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        numLlevadasPcLabel.setForeground(new java.awt.Color(255, 255, 255));
        numLlevadasPcLabel.setText("Cartón: 0");

        puntosPcLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        puntosPcLabel.setForeground(new java.awt.Color(255, 255, 255));
        puntosPcLabel.setText("Puntos: 0");

        puntosLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        puntosLabel.setForeground(new java.awt.Color(255, 255, 255));
        puntosLabel.setText("Puntos: 0");

        numLlevadasLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        numLlevadasLabel.setForeground(new java.awt.Color(255, 255, 255));
        numLlevadasLabel.setText("Cartón: 0");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Jugador 1");

        javax.swing.GroupLayout texturaPanel1Layout = new javax.swing.GroupLayout(texturaPanel1);
        texturaPanel1.setLayout(texturaPanel1Layout);
        texturaPanel1Layout.setHorizontalGroup(
            texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(texturaPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(texturaPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(texturaPanel1Layout.createSequentialGroup()
                                    .addComponent(cartaButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(texturaPanel1Layout.createSequentialGroup()
                                    .addComponent(cartaButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(texturaPanel1Layout.createSequentialGroup()
                                .addComponent(cartaButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cartaButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cartaButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cartaButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cartaButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(numLlevadasPcLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(puntosPcLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11))))
                    .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, texturaPanel1Layout.createSequentialGroup()
                            .addGap(226, 226, 226)
                            .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, texturaPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(11, 11, 11))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, texturaPanel1Layout.createSequentialGroup()
                                    .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(numLlevadasLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(puntosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(76, 76, 76)
                                    .addComponent(cartaButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cartaButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jLabel1)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        texturaPanel1Layout.setVerticalGroup(
            texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(texturaPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(texturaPanel1Layout.createSequentialGroup()
                        .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(texturaPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cartaButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cartaButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cartaButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cartaButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cartaButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(texturaPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(numLlevadasPcLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(puntosPcLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 255, Short.MAX_VALUE)
                        .addComponent(puntosLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numLlevadasLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(texturaPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cartaButton11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cartaButton12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cartaButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cartaButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cartaButton15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cartaButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cartaButton19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cartaButton20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cartaButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cartaButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cartaButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cartaButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cartaButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(texturaPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cartaButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cartaButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)))
                .addContainerGap())
        );

        jMenu1.setText("Juego ");

        menuJuegoNuevo.setText("Juego Nuevo");
        menuJuegoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuJuegoNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(menuJuegoNuevo);

        menuModoCarrera.setText("Modo Carrera");
        menuModoCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuModoCarreraActionPerformed(evt);
            }
        });
        jMenu1.add(menuModoCarrera);

        menuTerminarJuego.setText("Terminar Juego");
        menuTerminarJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTerminarJuegoActionPerformed(evt);
            }
        });
        jMenu1.add(menuTerminarJuego);

        menuNivel.setText("Nivel");

        radioButtonNivelUNO.setSelected(true);
        radioButtonNivelUNO.setText("Nivel 1");
        menuNivel.add(radioButtonNivelUNO);

        radioButtonNivelDOS.setSelected(true);
        radioButtonNivelDOS.setText("Nivel 2");
        menuNivel.add(radioButtonNivelDOS);

        jMenu1.add(menuNivel);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        menuInstrucciones.setText("Instrucciones");
        jMenu2.add(menuInstrucciones);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(texturaPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(texturaPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuJuegoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJuegoNuevoActionPerformed
        // TODO add your handling code here:
        iniciarJuego();
    }//GEN-LAST:event_menuJuegoNuevoActionPerformed

    private void menuTerminarJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTerminarJuegoActionPerformed
        // TODO add your handling code here:
      if(juego.carrera){try {
                juego.modoCarrera.GuardarXML("CuarentaMC.xml", ModoCarrera.class, juego.modoCarrera);
            } catch (JAXBException ex) {
                Logger.getLogger(FrMesa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FrMesa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FrMesa.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
        suspenderJuego();
     
       
    }//GEN-LAST:event_menuTerminarJuegoActionPerformed

    private void menuModoCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuModoCarreraActionPerformed
        // TODO add your handling code here:
        ModoCarrera mc=null;
        try {
            mc = ModoCarrera.CargarXML("CuarentaMC.xml", ModoCarrera.class);
        } catch (JAXBException ex) {
            Logger.getLogger(FrMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       iniciarJuego(true, mc);
        
    }//GEN-LAST:event_menuModoCarreraActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private cuarenta.CartaButton cartaButton1;
    private cuarenta.CartaButton cartaButton10;
    private cuarenta.CartaButton cartaButton11;
    private cuarenta.CartaButton cartaButton12;
    private cuarenta.CartaButton cartaButton13;
    private cuarenta.CartaButton cartaButton14;
    private cuarenta.CartaButton cartaButton15;
    private cuarenta.CartaButton cartaButton16;
    private cuarenta.CartaButton cartaButton17;
    private cuarenta.CartaButton cartaButton18;
    private cuarenta.CartaButton cartaButton19;
    private cuarenta.CartaButton cartaButton2;
    private cuarenta.CartaButton cartaButton20;
    private cuarenta.CartaButton cartaButton3;
    private cuarenta.CartaButton cartaButton4;
    private cuarenta.CartaButton cartaButton5;
    private cuarenta.CartaButton cartaButton6;
    private cuarenta.CartaButton cartaButton7;
    private cuarenta.CartaButton cartaButton8;
    private cuarenta.CartaButton cartaButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuInstrucciones;
    private javax.swing.JMenuItem menuJuegoNuevo;
    private javax.swing.JMenuItem menuModoCarrera;
    private javax.swing.JMenu menuNivel;
    private javax.swing.JMenuItem menuTerminarJuego;
    private javax.swing.JLabel numLlevadasLabel;
    private javax.swing.JLabel numLlevadasPcLabel;
    private javax.swing.JLabel puntosLabel;
    private javax.swing.JLabel puntosPcLabel;
    private javax.swing.JRadioButtonMenuItem radioButtonNivelDOS;
    private javax.swing.JRadioButtonMenuItem radioButtonNivelUNO;
    private cuarenta.texturaPanel texturaPanel1;
    // End of variables declaration//GEN-END:variables

    public void cuentaPuntosCarton(Jugador j){
        int carton=j.getLlevadas().size();
        if(carton>19){
        int puntos =carton%19+5;
    
        if(puntos%2!=0){
        puntos++;
           }
       
        if(!j.esta38){
             if(puntos>0){
                 
                 String nombre="";
                 if(j.equals(juego.getJugador())) nombre="Tienes: ";
                 else if(j.equals(juego.getPc())) nombre="PC tiene: ";
          JOptionPane.showMessageDialog(null,nombre+carton+" cartas"+"\n + "+puntos+"´puntos");
        }
         j.setPuntos(j.getPuntos()+puntos);
       
          }
        }
    }
    
    public void cuentaCarton() {
         cuentaPuntosCarton(juego.getJugador());
       actualizaPuntosLabel(juego.getJugador());
   juego.reiniciaLlevadas(juego.getJugador());  
   actualizaCartonLabel(juego.getJugador());
   
         cuentaPuntosCarton(juego.getPc());
       actualizaPuntosLabel(juego.getPc());
       juego.reiniciaLlevadas(juego.getPc());  
         actualizaCartonLabel(juego.getPc());
         
         juego.reiniciaMesa();
         
         if(juego.hayGanador()==1){
                    JOptionPane.showMessageDialog(null, "Gano J1");
                   if(juego.carrera) {
                          ModoCarrera mc=juego.modoCarrera;
                           mc.setNivel(mc.getNivel()+1);
                       reiniciarJuegoCarrera();}
                   else suspenderJuego();
                    } 
           if(juego.hayGanador()==2){
                    JOptionPane.showMessageDialog(null, "Gano PC");
                    if(juego.carrera) reiniciarJuegoCarrera();
                   else suspenderJuego();
                    } 
       
    }
      
    public void actualizaPuntosLabel(Jugador j){
        if(j.equals( juego.getJugador()) )
     this.puntosLabel.setText("Puntos: "+juego.getJugador().getPuntos());
        if(j.equals(juego.getPc()))
             this.puntosPcLabel.setText("Puntos: "+juego.getPc().getPuntos());
    
        
            }
    
    public void muestraMensaje(  final int n){
   SwingUtilities.invokeLater(
           new Runnable() {

            @Override
            public void run() {
                String msj=null;
                if(n==9) msj="Culantro!";
                if(n==10) msj="Kasandra!";
                
            }
        }
    
    
    );
        
    
       
    }
    
    public void habilitar(boolean habilitar){
    
        for(CartaButton cb: manoJ1){
          if(cb.carta!=null)
              cb.habilitada=habilitar;
          
      }
    
        for(CartaButton cb: mesa){
          if(cb.carta!=null)
              cb.habilitada=habilitar;
          
      } 
     
    }
   
}
