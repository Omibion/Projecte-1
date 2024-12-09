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
            JPanel panelTemporades = new Temporades_JPanel();
            JPanel panelEquips = new EquipsConsulta_JPanel();
            JPanel panelCategories = new CreaEquip_JPanel();
            
            JPanel panelJugadors = new JPanel();
            panelJugadors.setBackground(Color.GREEN);

            // Obtener el panel central del FramePrincipal
            JPanel panelCentro = frame.getPanelCentro();
            CardLayout cardLayout = new CardLayout();
            panelCentro.setLayout(cardLayout);

            // Agregar los paneles al CardLayout
            panelCentro.add(panelTemporades, "Temporades");
            panelCentro.add(panelEquips, "Equips");
            panelCentro.add(panelCategories, "Categories");
            panelCentro.add(panelJugadors, "Jugadors");

            // Configurar acciones de los botones
            frame.temp.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Temporades"));
            frame.equip.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Equips"));
            frame.cat.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Categories"));
            frame.jugadors.addActionListener((ActionEvent e) -> cardLayout.show(panelCentro, "Jugadors"));

            // Mostrar el frame
            frame.setVisible(true);
        });
    }
}
