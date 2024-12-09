package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TestFramePrincipal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear la ventana principal
            FramePrincipal frame = new FramePrincipal();

            // Crear paneles para mostrar
            JPanel panelTemporades = new Temporades_JPanel();   // Panel de Temporades
            JPanel panelEquips = new EquipsConsulta_JPanel();   // Panel de Equips
            JPanel panelCategories = new Categories_JPanel();   // Panel de Categories
            
            // Crear un ejemplo básico para Jugadors
            JPanel panelJugadors = new JPanel();
            panelJugadors.setBackground(Color.GREEN); // Simplemente un color para identificarlo
            panelJugadors.add(new JLabel("Panel de Jugadors"));

            // Obtener el panel central del FramePrincipal
            JPanel panelCentro = frame.getPanelCentro();
            CardLayout cardLayout = new CardLayout(); // Configurar CardLayout para cambiar entre paneles
            panelCentro.setLayout(cardLayout);

            // Agregar los paneles al CardLayout con identificadores
            panelCentro.add(panelTemporades, "Temporades");
            panelCentro.add(panelEquips, "Equips");
            panelCentro.add(panelCategories, "Categories");
            panelCentro.add(new Jugadors_JPanel(), "Jugadors");

            // Configurar las acciones de los botones para cambiar de panel
            frame.temp.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Temporades"));
            frame.equip.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Equips"));
            frame.cat.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Categories"));
            frame.jugadors.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Jugadors"));

            // Mostrar el frame
            frame.setVisible(true);
        });
    }
}
