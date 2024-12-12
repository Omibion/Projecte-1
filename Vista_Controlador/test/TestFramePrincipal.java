
import Vista.EquipsConsulta_JPanel;
import Vista.FitxaJugador_JPanel;
import Vista.FramePrincipal;
import Vista.Jugadors_JPanel;
import Vista.Temporades_JPanel;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestFramePrincipal {
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TestFramePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SwingUtilities.invokeLater(() -> {
            // Crear la ventana principal
            FramePrincipal frame = new FramePrincipal();

            // Crear paneles para mostrar
            JPanel panelTemporades = new Temporades_JPanel();
            JPanel panelEquips = new EquipsConsulta_JPanel();
            JPanel panelJugadors = new Jugadors_JPanel(); // Panel de Jugadors
            JPanel panelficha = new FitxaJugador_JPanel(); // Panel de Ficha de Jugador

            // Obtener el panel central del FramePrincipal
            JPanel panelCentro = frame.getPanelCentro();
            CardLayout cardLayout = new CardLayout(); // Configurar CardLayout para cambiar entre paneles
            panelCentro.setLayout(cardLayout);

            // Agregar los paneles al CardLayout con identificadores
            panelCentro.add(panelTemporades, "Temporades");
            panelCentro.add(panelEquips, "Equips");
            panelCentro.add(panelJugadors, "Jugadors");
            panelCentro.add(panelficha, "Categoria");

            // Configurar las acciones de los botones para cambiar de panel
            frame.temp.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Temporades"));
            frame.equip.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Equips"));
            frame.jugadors.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Jugadors"));
            frame.cat.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Categoria")); // Cambia a Categoria

            // Mostrar el frame
            frame.setVisible(true);
        });
    }
}
