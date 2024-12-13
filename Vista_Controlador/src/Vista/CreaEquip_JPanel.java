package Vista;

import javax.swing.*;
import java.awt.*;

public class CreaEquip_JPanel extends JPanel {
    JLabel titol, nom, temporada, categoria;
    JComboBox<String> temporades, categories;
    JTextField e_nom;
    JButton guarda, torna;
    JRadioButton masc, fem, mix;
    ButtonGroup grup;

    public CreaEquip_JPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; 

        titol = new JLabel("Crea/Edita Equip");
        titol.setFont(new Font("Arial", Font.BOLD, 16));
        titol.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1; 
        add(titol, gbc);

        nom = new JLabel("Nombre:");
        nom.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(nom, gbc);

        e_nom = new JTextField();
        e_nom.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(e_nom, gbc);

        temporada = new JLabel("Temporada:");
        temporada.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(temporada, gbc);

        temporades = new JComboBox<>(new String[]{"2021", "2022", "2023", "2024"});
        temporades.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(temporades, gbc);

        categoria = new JLabel("Categoría:");
        categoria.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(categoria, gbc);

        categories = new JComboBox<>(new String[]{"Senior", "Junior", "Infantil"});
        categories.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(categories, gbc);
        masc = new JRadioButton("Masculino");
        fem = new JRadioButton("Femenino");
        mix = new JRadioButton("Mixto");
        masc.setFont(new Font("Arial", Font.PLAIN, 16));
        fem.setFont(new Font("Arial", Font.PLAIN, 16));
        mix.setFont(new Font("Arial", Font.PLAIN, 16));
        grup = new ButtonGroup();
        grup.add(masc);
        grup.add(fem);
        grup.add(mix);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        radioPanel.add(masc);
        radioPanel.add(fem);
        radioPanel.add(mix);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(radioPanel, gbc);

        guarda = new JButton("Guardar");
        torna = new JButton("Volver");
        guarda.setFont(new Font("Arial", Font.BOLD, 16));
        torna.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(10, 10, 10, 10);

        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.anchor = GridBagConstraints.SOUTHWEST;
        buttonPanel.add(guarda, buttonGbc);
        buttonGbc.gridx = 1;
        buttonGbc.gridy = 0;
        buttonGbc.anchor = GridBagConstraints.SOUTHEAST;
        buttonPanel.add(torna, buttonGbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 0.3; 
        add(buttonPanel, gbc);
    }
}
