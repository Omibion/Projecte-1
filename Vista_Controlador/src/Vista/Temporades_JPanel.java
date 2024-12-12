package Vista;

import java.awt.*;
import javax.swing.*;
//LA desició de diseny d'aquest JPanel es per que sigui més intuitiu a l'hora de crear temporades, o aixo es el que pretenc
public class Temporades_JPanel extends JPanel {
    private JLabel labelTitulo,labelNovaTemp;
    private JComboBox<String> comboAnyInici;
    private JTable tablaTemporadas;
    private JButton btnCrear, btnEsborrar;

    public Temporades_JPanel() {
  
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        labelTitulo = new JLabel("Gestió de temporades");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10); // Más separación bajo el título
        gbc.anchor = GridBagConstraints.WEST;
        this.add(labelTitulo, gbc);
        labelNovaTemp = new JLabel("Escull l'any d'inici de la nova temporada");
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.insets = new Insets(0, 10, 5, 10);
        gbc.weightx = 0.5;
        this.add(labelNovaTemp,gbc);
        comboAnyInici = new JComboBox<>(new String[]{"2023", "2024", "2025", "2026"});
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 10, 20, 10); // Más separación bajo el ComboBox
        this.add(comboAnyInici, gbc);

        btnCrear = new JButton("Crear temporada");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 10, 20, 10); // Más separación bajo el botón
        this.add(btnCrear, gbc);

        String[] columnNames = {"Temporada", ""};
        Object[][] data = {
            {"21-22", true},
            {"22-23", true},
            {"23-24", false},
            {"24-25", false}
        };
        tablaTemporadas = new JTable(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? Boolean.class : String.class;
            }
        };
        JScrollPane scrollPane = new JScrollPane(tablaTemporadas);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2; 
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 10, 10); // Más espacio entre tabla y otros elementos
        this.add(scrollPane, gbc);
        btnEsborrar = new JButton("Esborrar seleccionat");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1; // Asegura que no ocupe toda la altura
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTH; // Alineado al norte
        gbc.insets = new Insets(0, 10, 10, 10); // Más espacio lateral y superior
        this.add(btnEsborrar, gbc);
    }

    public JComboBox<String> getComboAnyInici() {
        return comboAnyInici;
    }

    public JTable getTablaTemporadas() {
        return tablaTemporadas;
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public JButton getBtnEsborrar() {
        return btnEsborrar;
    }
}
