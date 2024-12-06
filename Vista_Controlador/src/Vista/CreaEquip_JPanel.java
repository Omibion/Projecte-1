package Vista;

import javax.swing.*;
import java.awt.*;

public class CreaEquip_JPanel extends JPanel {

    JLabel titol;
    JLabel nom;
    JLabel temporada;
    JLabel categoria;
    JComboBox<String> temporades;
    JComboBox<String> categories;
    JTextField e_nom;
    JButton guarda;
    JButton torna;
    ButtonGroup grup;
    JRadioButton masc;
    JRadioButton fem;
    JRadioButton mix;

    public CreaEquip_JPanel() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        titol = new JLabel("Crear Equipo");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;  
        add(titol, gbc);

        nom = new JLabel("Nombre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;  
        add(nom, gbc);
        e_nom = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(e_nom, gbc);


        temporada = new JLabel("Temporada:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(temporada, gbc);

        temporades = new JComboBox<>();  
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(temporades, gbc);

        categoria = new JLabel("Categoría:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(categoria, gbc);
        categories = new JComboBox<>();  
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(categories, gbc);
        masc = new JRadioButton("Masculino");
        fem = new JRadioButton("Femenino");
        mix = new JRadioButton("Mixto");
        grup = new ButtonGroup();
        grup.add(masc);
        grup.add(fem);
        grup.add(mix);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(masc);
        radioPanel.add(fem);
        radioPanel.add(mix);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(radioPanel, gbc);
        guarda = new JButton("Guardar");
        torna = new JButton("Borrar");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(guarda); 
        buttonPanel.add(torna);  
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }
}
