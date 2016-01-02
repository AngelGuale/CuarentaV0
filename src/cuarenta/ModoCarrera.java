/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mtech
 */@XmlRootElement
public class ModoCarrera {
    
    private int nivel;
    private int juegosJugados;
    private int juegosPerdidos;
    private int juegosGanados;
    
    private int ganadosAhora;

    public ModoCarrera() {
    }

    public ModoCarrera(int nivel, int juegosJugados, int juegosPerdidos, int juegosGanados, int ganadosAhora) {
        this.nivel = nivel;
        this.juegosJugados = juegosJugados;
        this.juegosPerdidos = juegosPerdidos;
        this.juegosGanados = juegosGanados;
        this.ganadosAhora = ganadosAhora;
    }
    
    

    public int getGanadosAhora() {
        return ganadosAhora;
    }

    public void setGanadosAhora(int ganadosAhora) {
        this.ganadosAhora = ganadosAhora;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }

    public void setJuegosGanados(int juegosGanados) {
        this.juegosGanados = juegosGanados;
    }

    public int getJuegosJugados() {
        return juegosJugados;
    }

    public void setJuegosJugados(int juegosJugados) {
        this.juegosJugados = juegosJugados;
    }

    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }

    public void setJuegosPerdidos(int juegosPerdidos) {
        this.juegosPerdidos = juegosPerdidos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
 
    
    public void GuardarXML(String NAME,Class<?> Class,Object o) throws JAXBException, FileNotFoundException, IOException
            {
        JAXBContext context;
        Marshaller marshaller;
        context = JAXBContext.newInstance(Class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        FileOutputStream fos = new FileOutputStream(NAME);
        marshaller.marshal(o, fos);
        fos.close();
            
            }
    public static ModoCarrera CargarXML(String NAME,Class<?> Class) throws JAXBException
    {
        JAXBContext context;
        Marshaller marshaller;
        context = JAXBContext.newInstance(Class);
        marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        
        //Juego j = (Juego) unmarshaller.unmarshal(new File(NAME));
         ModoCarrera j = (ModoCarrera) unmarshaller.unmarshal(new File(NAME));
        
        return j;
        
        
    }
    public static void main(String[] args){
//    ModoCarrera mc=new ModoCarrera(1, 0 , 0, 0, 0);
//        try {
//            mc.GuardarXML("CuarentaMC.xml", ModoCarrera.class, mc);
//        } catch (JAXBException ex) {
//            Logger.getLogger(ModoCarrera.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ModoCarrera.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ModoCarrera.class.getName()).log(Level.SEVERE, null, ex);
//        }
//  
        JFrame f=new JFrame();
       
        JTextArea jt=new JTextArea();
        jt.append("jajajaja");
         f.add(jt);
         f.setVisible(true);
    }
    
}
