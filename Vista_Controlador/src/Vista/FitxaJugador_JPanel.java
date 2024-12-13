package Vista;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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
    private JLabel titol, nomJug, cognomsJug, nifJug, sexeJug, adreçaJug, cpJug, poblacioJug, dataNaixJug, fotoJug,imagenLabel;
    private JTextField textNom, textCognoms, textNif, textAdreça, textCp, textPoblacio;
    private ButtonGroup grpSexe;
    private JRadioButton rdHome, rdDona;
    private DatePicker datePicker;
    private JFormattedTextField cal; 
    
    public FitxaJugador_JPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre los componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicializar componentes
        cal = new JFormattedTextField();
        datePicker = new DatePicker();
        datePicker.setEditor(cal);
        
        titol = new JLabel("Fitxa del jugador");
        titol.setFont(new Font("Arial", Font.BOLD, 16));
        nomJug = new JLabel("Nom:");
        cognomsJug = new JLabel("Cognoms:");
        nifJug = new JLabel("NIF:");
        sexeJug = new JLabel("Sexe:");
        adreçaJug = new JLabel("Adreça:");
        cpJug = new JLabel("Codi Postal:");
        poblacioJug = new JLabel("Població:");
        dataNaixJug = new JLabel("Data de naixement:");

        // Aquí agregamos la imagen del jugador
        fotoJug = new JLabel();
        try {
            ImageIcon foto = new ImageIcon(getClass().getResource("/img/placeholder-male.jpg"));
            fotoJug.setIcon(foto);
        } catch (Exception e) {
            e.printStackTrace();
            fotoJug.setText("Imagen no disponible");
        }

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
         imagenLabel = new JLabel();
        String rutaImagen = "/img/placeholder-male.jpg";
        ImageIcon iconoRedimensionado = redimensionarImagen(rutaImagen, 200, 200);
        if (iconoRedimensionado != null) {
            imagenLabel.setIcon(iconoRedimensionado);
        } else {
            imagenLabel.setText("No se encontró imagen");
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titol, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(nomJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(textNom, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(cognomsJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(textCognoms, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(nifJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(textNif, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(sexeJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        JPanel sexePanel = new JPanel();
        sexePanel.add(rdHome);
        sexePanel.add(rdDona);
        add(sexePanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(adreçaJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(textAdreça, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(cpJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(textCp, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(poblacioJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(textPoblacio, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(dataNaixJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 8;
        add(cal, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight=5;
        add(imagenLabel, gbc);
    }
    private ImageIcon redimensionarImagen(String rutaImagen, int anchoDeseado, int altoDeseado) {
    try {
        java.net.URL url = getClass().getResource(rutaImagen);
        if (url == null) {
            System.err.println("No se encontró el recurso: " + rutaImagen);
            return null;
        }
        ImageIcon originalIcon = new ImageIcon(url);
        Image imagenOriginal = originalIcon.getImage();
        int anchoOriginal = originalIcon.getIconWidth();
        int altoOriginal = originalIcon.getIconHeight();
        double proporcion = (double) anchoOriginal / altoOriginal;
        int nuevoAncho, nuevoAlto;
        if ((double) anchoDeseado / altoDeseado > proporcion) {
            nuevoAlto = altoDeseado;
            nuevoAncho = (int) (altoDeseado * proporcion);
        } else {
            nuevoAncho = anchoDeseado;
            nuevoAlto = (int) (anchoDeseado / proporcion);
        }
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    } catch (Exception e) {
        System.err.println("Error al redimensionar la imagen: " + e.getMessage());
        return null;
    }
}

}
