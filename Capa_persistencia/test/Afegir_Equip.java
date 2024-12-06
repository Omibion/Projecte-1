
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
public class Afegir_Equip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        conexio_BBDD cone;
       try {
            cone = new conexio_BBDD();
           
           cone.afegir_equip("Los Mejores", 'M', 1, 1);
           cone.conectionClose();
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Prova_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
