package Vista;

import java.awt.*;
import javax.swing.*;

public class Temporades_JPanel extends JPanel {
    JLabel text;
    JTable taula;
    JButton afegir;
    JButton esborrar;

    public Temporades_JPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen consistente
        gbc.fill = GridBagConstraints.BOTH;

        // Etiqueta
        text = new JLabel("Afegir i eliminar temporades:");
        text.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        this.add(text, gbc);

        // Tabla
        taula = new JTable(5, 3);
        JScrollPane scroll = new JScrollPane(taula);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        this.add(scroll, gbc);

        // Panel para los botones en la parte inferior
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Usamos GridLayout para alinear los botones
        afegir = new JButton("Afegir temporada");
        esborrar = new JButton("Eliminar temporada");

        bottomPanel.add(afegir);
        bottomPanel.add(esborrar);

        // Añadimos el panel de botones en la parte inferior
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        this.add(bottomPanel, gbc);
    }
}
