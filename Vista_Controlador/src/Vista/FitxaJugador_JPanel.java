package Vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import raven.datetime.component.date.DatePicker;

public class FitxaJugador_JPanel extends JPanel {
    // Declaración de componentes
    private JLabel titol, nomJug, cognomsJug, nifJug, sexeJug, adreçaJug, cpJug, poblacioJug, dataNaixJug;
    private JTextField textNom, textCognoms, textNif, textAdreça, textCp, textPoblacio;
    private ButtonGroup grpSexe;
    private JRadioButton rdHome, rdDona;
    private DatePicker datePicker;
    private JFormattedTextField cal; 
    public FitxaJugador_JPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicializar componentes
        cal = new JFormattedTextField();
        datePicker= new DatePicker();
        datePicker.setEditor(cal);
        
        titol = new JLabel("Fitxa del jugador");
        nomJug = new JLabel("Nom:");
        cognomsJug = new JLabel("Cognoms:");
        nifJug = new JLabel("NIF:");
        sexeJug = new JLabel("Sexe:");
        adreçaJug = new JLabel("Adreça:");
        cpJug = new JLabel("Codi Postal:");
        poblacioJug = new JLabel("Població:");
        dataNaixJug = new JLabel("Data de naixement:");

        textNom = new JTextField(20);
        textCognoms = new JTextField(20);
        textNif = new JTextField(15);
        textAdreça = new JTextField(20);
        textCp = new JTextField(5);
        textPoblacio = new JTextField(20);

        rdHome = new JRadioButton("Home");
        rdDona = new JRadioButton("Dona");
        grpSexe = new ButtonGroup();
        grpSexe.add(rdHome);
        grpSexe.add(rdDona);

        datePicker = new DatePicker();

        // Añadir componentes al panel
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titol, gbc);

        // Campo Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nomJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(textNom, gbc);

        // Campo Apellidos
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(cognomsJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(textCognoms, gbc);

        // Campo NIF
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(nifJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(textNif, gbc);

        // Campo Sexo
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(sexeJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JPanel sexePanel = new JPanel();
        sexePanel.add(rdHome);
        sexePanel.add(rdDona);
        add(sexePanel, gbc);

        // Campo Dirección
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(adreçaJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(textAdreça, gbc);

        // Campo Código Postal
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(cpJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(textCp, gbc);

        // Campo Población
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(poblacioJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        add(textPoblacio, gbc);

        // Campo Fecha de Nacimiento
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(dataNaixJug, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        add(datePicker, gbc);
    }
}

 
