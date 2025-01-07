package Vista;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import raven.datetime.component.date.DatePicker;

public class FitxaJugador_JPanel extends JPanel {
    private JLabel titol, nomJug, cognomsJug, nifJug, sexeJug, adreçaJug, cpJug, poblacioJug, dataNaixJug, fotoJug, imagenLabel,dataRevisioMedicaJug,ibanLabel;
    private JTextField textNom, textCognoms, textNif, textAdreça, textCp, textPoblacio,textIban;
    private ButtonGroup grpSexe;
    private JRadioButton rdHome, rdDona;
    private DatePicker datePicker,datePickerRevisio;
    private JFormattedTextField cal,calRevisio; 
    private JButton btnDesa, btnTorna, btnCanviarFoto;
    String rutaImagen;
    private ImageIcon foto;

    public FitxaJugador_JPanel() {
     
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cal = new JFormattedTextField();
        datePicker = new DatePicker();
        datePicker.setEditor(cal);
        
         calRevisio = new JFormattedTextField();
        datePickerRevisio = new DatePicker();
        datePickerRevisio.setEditor(calRevisio);
        
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
        dataRevisioMedicaJug = new JLabel("Data Revisió Mèdica:");
        ibanLabel = new JLabel("IBAN:");
        textIban = new JTextField(22);

        fotoJug = new JLabel();
        try {
            foto = new ImageIcon(getClass().getResource("/img/placeholder-male.jpg"));
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
        rutaImagen = "C:/Users/isard/Documents/Projecte-1/Vista_Controlador/src/img/placeholder-male.jpg";
        ImageIcon iconoRedimensionado = redimensionarImagen(rutaImagen, 200, 200);
        if (iconoRedimensionado != null) {
            imagenLabel.setIcon(iconoRedimensionado);
        } else {
            imagenLabel.setText("No s'ha trobat la imatge");
        }

        btnDesa = new JButton("Desa");
        btnTorna = new JButton("Torna");
        btnCanviarFoto = new JButton("Canviar Foto");

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
        gbc.gridheight = 6;
        add(imagenLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 7; 
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(btnCanviarFoto, gbc);

         gbc.gridx = 0;
        gbc.gridy = 9;
        add(dataRevisioMedicaJug, gbc);
        gbc.gridx = 1;
        gbc.gridy = 9;
        add(calRevisio, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(ibanLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 10;
        add(textIban, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(btnDesa, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        add(btnTorna, gbc);
    }
    
    public ImageIcon redimensionarImagen(String rutaImagen, int anchoDeseado, int altoDeseado) {
    try {
        File archivo = new File(rutaImagen); 
        if (!archivo.exists()) {
            System.err.println("El archivo no existe: " + rutaImagen);
            return null;
        }

        ImageIcon originalIcon = new ImageIcon(rutaImagen);
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
        System.err.println("Error al redimensionar la imatge: " + e.getMessage());
        return null;
    }
}

   
       public void setBtnCanviarFotoActionListener(ActionListener listener) {
        btnCanviarFoto.addActionListener(listener);
    }

    public JTextField getTextNom() {
        return textNom;
    }

    public void setTextNom(JTextField textNom) {
        this.textNom = textNom;
    }

    public JTextField getTextCognoms() {
        return textCognoms;
    }

    public void setTextCognoms(JTextField textCognoms) {
        this.textCognoms = textCognoms;
    }

    public JTextField getTextNif() {
        return textNif;
    }

    public void setTextNif(JTextField textNif) {
        this.textNif = textNif;
    }

    public JTextField getTextAdreça() {
        return textAdreça;
    }

    public void setTextAdreça(JTextField textAdreça) {
        this.textAdreça = textAdreça;
    }

    public JTextField getTextCp() {
        return textCp;
    }

    public void setTextCp(JTextField textCp) {
        this.textCp = textCp;
    }

    public JTextField getTextPoblacio() {
        return textPoblacio;
    }

    public void setTextPoblacio(JTextField textPoblacio) {
        this.textPoblacio = textPoblacio;
    }

    public ButtonGroup getGrpSexe() {
        return grpSexe;
    }

    public void setGrpSexe(ButtonGroup grpSexe) {
        this.grpSexe = grpSexe;
    }

    public JRadioButton getRdHome() {
        return rdHome;
    }

    public void setRdHome(JRadioButton rdHome) {
        this.rdHome = rdHome;
    }

    public JRadioButton getRdDona() {
        return rdDona;
    }

    public void setRdDona(JRadioButton rdDona) {
        this.rdDona = rdDona;
    }

    public JTextField getTextIban() {
        return textIban;
    }

    public void setTextIban(JTextField textIban) {
        this.textIban = textIban;
    }

    public JFormattedTextField getCal() {
        return cal;
    }

    public void setCal(JFormattedTextField cal) {
        this.cal = cal;
    }

    public JFormattedTextField getCalRevisio() {
        return calRevisio;
    }

    public void setCalRevisio(JFormattedTextField calRevisio) {
        this.calRevisio = calRevisio;
    }

    public void setFotoJug(JLabel fotoJug) {
        this.fotoJug = fotoJug;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public JButton getBtnDesa() {
        return btnDesa;
    }

    public JButton getBtnTorna() {
        return btnTorna;
    }

    public JButton getBtnCanviarFoto() {
        return btnCanviarFoto;
    }

    public ImageIcon getFoto() {
        return foto;
    }

    public void setFoto(ImageIcon foto) {
        this.foto = foto;
    }

    public JLabel getImagenLabel() {
        return imagenLabel;
    }

    public void setImagenLabel(JLabel imagenLabel) {
        this.imagenLabel = imagenLabel;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public DatePicker getDatePickerRevisio() {
        return datePickerRevisio;
    }
    
}
