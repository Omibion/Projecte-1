
import david.milaifontanals.org.Temporada;
import java.util.ArrayList;
import david.milaifontanals.org.conexio_BBDD;
import david.milaifontanals.org.gestorEquipsException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author isard
 */
public class Carregar_temporades {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws gestorEquipsException {
        conexio_BBDD cone;
        cone = new conexio_BBDD();
        ArrayList<Temporada> ar = new ArrayList();
        ar=cone.carregar_temporades();
        for (Temporada temporada : ar) {
            System.out.println(""+temporada.toString()); 
            
        }
    }
    
}
