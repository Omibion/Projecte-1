package vista_controlador;

import Vista.FRameLoggin;
import Vista.FramePrincipal;
import Vista.*;
import david.milaifontanals.org.Interficie_persistencia;
import david.milaifontanals.org.Temporada;
import david.milaifontanals.org.conexio_BBDD;
import david.milaifontanals.org.gestorEquipsException;
import david.milaifontanals.org.usuari;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;

public class Vista_Controlador implements ActionListener {
    private FRameLoggin fl;
    private FramePrincipal fp;
    private Interficie_persistencia persistencia;

    public Vista_Controlador() throws gestorEquipsException {
        try {    
            this.persistencia = new conexio_BBDD();  
        } catch (gestorEquipsException ex) {
            throw new gestorEquipsException("Error al inicializar la capa de persistencia", ex);
        }
        this.fl = new FRameLoggin();
        this.fp = new FramePrincipal();

        JPanel panelCentro = fp.getPanelCentro();
        CardLayout cardLayout = new CardLayout();
        panelCentro.setLayout(cardLayout);

        JPanel panelTemporades = new Temporades_JPanel();
        JPanel panelEquips = new EquipsConsulta_JPanel();
        JPanel panelJugadors = new Jugadors_JPanel(); 
        JPanel panelficha = new FitxaJugador_JPanel();

        panelCentro.add(panelTemporades, "Temporades");
        panelCentro.add(panelEquips, "Equips");
        panelCentro.add(panelJugadors, "Jugadors");

        fp.temp.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Temporades"));
        fp.equip.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Equips"));
        fp.jugadors.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Jugadors"));
 

        this.fl.getLoginButton().addActionListener(this);
        this.fl.setVisible(true);
        cargarTemporadasEnVista();
    }
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == fl.getLoginButton()) {
        String usuario = fl.getUsuari().getText();
        String password = new String(fl.getContrasenya().getPassword()); 
       
        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(fl, "Por favor, ingrese ambos campos", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        try {
            password = persistencia.encriptar_contrasenya(password);
        } catch (gestorEquipsException ex) {
            ex.printStackTrace();
        }

        try {
            
            usuari u = persistencia.obtenir_usuari(usuario);
            
            System.out.println(""+u.getLogin().toString());
            if (u != null && u.getContrasenya().equals(password)) {
                System.out.println("Login correcto");
              
                fl.setVisible(false);
                fp.setVisible(true);
            } else {
            
                JOptionPane.showMessageDialog(fl, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                fl.getUsuari().setText("");  
                fl.getContrasenya().setText(""); 
                fl.getUsuari().requestFocus(); 
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(fl, "Error al validar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            fl.getUsuari().setText(""); 
            fl.getContrasenya().setText(""); 
            fl.getUsuari().requestFocus();  
        }
    }
}
private  void cargarTemporadasEnVista() {
    try {
      
        ArrayList<Temporada> listaTemporadas = persistencia.carregar_temporades();
        
        Temporades_JPanel panelTemporades = (Temporades_JPanel) fp.getPanelCentro().getComponent(0); 
        panelTemporades.cargarTemporadas(listaTemporadas);
    } catch (gestorEquipsException ex) {
        JOptionPane.showMessageDialog(fp, "Error al cargar las temporadas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public static void main(String[] args) {
        try {
            new Vista_Controlador();
        } catch (gestorEquipsException ex) {
            ex.printStackTrace(); 
        }
    }
}
