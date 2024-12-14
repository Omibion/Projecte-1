package Vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import david.milaifontanals.org.Temporada;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

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

        // Inicializar la tabla con un modelo vacío
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
    }

    // Métodos para obtener los elementos de la vista
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

  
public void cargarTemporadas(ArrayList<Temporada> listaTemporadas) {
    // Obtén el modelo de la tabla
    DefaultTableModel model = (DefaultTableModel) tablaTemporadas.getModel();
    
    // Limpiar las filas existentes
    model.setRowCount(0);

    // Formateador para obtener solo los dos últimos dígitos del año
    SimpleDateFormat sdf = new SimpleDateFormat("yy");

    // Itera sobre la lista de temporadas y agrega las filas a la tabla
    for (Temporada temporada : listaTemporadas) {
        System.out.println("" + temporada.toString());
        
        // Creamos un array de objetos para la fila de la tabla
        Object[] rowData = new Object[2];
        
        // Formateamos las fechas de inicio y fin para obtener los dos últimos dígitos del año
        String anyIniStr = sdf.format(temporada.getAnyIni());
        String anyFiStr = sdf.format(temporada.getAnyFi());

        // Concatenamos el rango de años en el formato "anyini-anyfi"
        String anyPeriodo = anyIniStr + "-" + anyFiStr;

        // Asignamos los valores a las celdas de la fila
        rowData[0] = anyPeriodo; // El rango de años "anyini-anyfi"
        rowData[1] = false; // Valor para la casilla de selección (puedes cambiarlo si es necesario)

        // Añadimos la fila al modelo de la tabla
        model.addRow(rowData);
    }
}


}
