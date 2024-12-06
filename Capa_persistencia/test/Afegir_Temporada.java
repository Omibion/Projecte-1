
import david.milaifontanals.org.conexio_BBDD;
import david.milaifontanals.org.gestorEquipsException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class Afegir_Temporada {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      conexio_BBDD cone;
       try {
            cone = new conexio_BBDD();
            int year = 2024;

      
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY); // Mes (enero = 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1);        // Día del mes

      
        Date utilDate = calendar.getTime();
        int year2 = 2025;

        // Crear una instancia de Calendar
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, year);
        calendar2.set(Calendar.MONTH, Calendar.JANUARY); // Mes (enero = 0)
        calendar2.set(Calendar.DAY_OF_MONTH, 1);        // Día del mes

     
        Date utilDate2 = calendar.getTime();
         java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime());
         java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        cone.afegir_temporada(sqlDate, sqlDate2);
           cone.conectionClose();
        } catch (gestorEquipsException ex) {
            Logger.getLogger(Prova_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
