/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cuarenta;

import java.util.Comparator;

/**
 *
 * @author Mtech
 */
public class comparaPorGanancia implements Comparator <Carta>
{

    @Override
    public int compare(Carta o1, Carta o2) {
        if(o1.getGanancia()==o2.getGanancia()){
        return 0;
        }
        else if(o1.getGanancia()>o2.getGanancia()){
        return 1;
        }
        else if(o1.getGanancia()<o2.getGanancia()){
        return -1;
        }
        return -1;
    }

}
