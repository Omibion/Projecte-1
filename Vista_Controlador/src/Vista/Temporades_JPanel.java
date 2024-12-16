package Vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import david.milaifontanals.org.Temporada;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Temporades_JPanel extends JPanel {
    private JLabel labelTitulo, labelNovaTemp;
    private JComboBox<String> comboAnyInici;
    private JTable tablaTemporadas;
    private JButton btnCrear, btnEsborrar, desarButton;

    public Temporades_JPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        labelTitulo = new JLabel("Gestió de temporades");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.WEST;
        this.add(labelTitulo, gbc);

        labelNovaTemp = new JLabel("Escull l'any d'inici de la nova temporada");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 5, 10);
        gbc.weightx = 0.5;
        this.add(labelNovaTemp, gbc);

        comboAnyInici = new JComboBox<>(new String[]{"2025", "2026", "2027", "2028"});
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 10, 20, 10);
        this.add(comboAnyInici, gbc);

        btnCrear = new JButton("Crear temporada");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 10, 20, 10); 
        this.add(btnCrear, gbc);

        String[] columnNames = {"Temporada", "Seleccionar"};

        tablaTemporadas = new JTable(new DefaultTableModel(columnNames, 0)) {
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
        gbc.insets = new Insets(0, 10, 10, 10);
        this.add(scrollPane, gbc);

        btnEsborrar = new JButton("Esborrar seleccionat");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 10, 10, 10); 
        this.add(btnEsborrar, gbc);

     
        desarButton = new JButton("Desar");
        gbc.gridx = 1;  
        gbc.gridy = 5; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(10, 10, 20, 10); // Agregar un poco de margen en la parte inferior
        this.add(desarButton, gbc);
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

    public JButton getDesarButton() {
        return desarButton;
    }

    public void cargarTemporadas(ArrayList<Temporada> listaTemporadas) {
        DefaultTableModel model = (DefaultTableModel) tablaTemporadas.getModel();
        model.setRowCount(0);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        for (Temporada temporada : listaTemporadas) {
            System.out.println("" + temporada.toString());

            Object[] rowData = new Object[2];

            String anyIniStr = sdf.format(temporada.getAnyIni());
            String anyFiStr = sdf.format(temporada.getAnyFi());

            String anyPeriodo = anyIniStr + "-" + anyFiStr;
            rowData[0] = anyPeriodo; 
            rowData[1] = false; 
            model.addRow(rowData);
        }
    }

public ArrayList<Temporada> getTemporadasSeleccionadas(ArrayList<Temporada> listaTemporadas) {
    ArrayList<Temporada> temporadasSeleccionadas = new ArrayList<>();
    DefaultTableModel model = (DefaultTableModel) tablaTemporadas.getModel();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Definir el formato de fecha esperado

    // Iteramos sobre todas las filas de la tabla
    for (int row = 0; row < model.getRowCount(); row++) {
        Boolean selected = (Boolean) model.getValueAt(row, 1);  // Suponiendo que la columna 1 es la casilla de selección
        if (selected != null && selected) {
            // Obtenemos los valores correspondientes a cada columna de la fila seleccionada
            String anyPeriodoStr = (String) model.getValueAt(row, 0);  // Suponiendo que la columna 0 es el periodo (como 'yyyy-yy')

            // Buscamos la temporada en la lista con el periodo correspondiente
            for (Temporada temporada : listaTemporadas) {
                String periodo = new SimpleDateFormat("yy").format(temporada.getAnyIni()) + "-" + 
                                 new SimpleDateFormat("yy").format(temporada.getAnyFi());

                if (anyPeriodoStr.equals(periodo)) {
                    temporadasSeleccionadas.add(temporada);
                    break; // 
                }
            }
        }
    }
    return temporadasSeleccionadas;
}


   

   

}
