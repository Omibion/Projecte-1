package Vista;

import david.milaifontanals.org.Categoria;
import david.milaifontanals.org.Jugador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Jugadors_JPanel extends JPanel {
    private JTable taulaJugadors;
    private JButton esborraSeleccionat, afegirJugador, exportarJugadors;
    private JTextField nifField, nombreField, fechaField;
    private JButton cercaButton, cercaPorNomButton, cercaPorFechaButton, cercaPorCategoriaButton;
    private DefaultTableModel model;
    private JComboBox<String> categoriaComboBox; 

  public Jugadors_JPanel() {
    setLayout(new BorderLayout(10, 10));

   
    JPanel topPanel = new JPanel(new GridLayout(2, 5, 10, 10));
    JLabel nifLabel = new JLabel("NIF");
    nifField = new JTextField(15);
    cercaButton = new JButton("Cerca");
    JLabel nombreLabel = new JLabel("Nom");
    nombreField = new JTextField(15);
    cercaPorNomButton = new JButton("Cerca ");
    JLabel fechaLabel = new JLabel("Data Naix");
    fechaField = new JTextField(15);
    cercaPorFechaButton = new JButton("Cerca");
    JLabel categoriaLabel = new JLabel("Categoría");
    categoriaComboBox = new JComboBox<>();
    cercaPorCategoriaButton = new JButton("Cerca");
    topPanel.add(nifLabel);
    topPanel.add(nifField);
    topPanel.add(cercaButton);
    topPanel.add(nombreLabel);
    topPanel.add(nombreField);
    topPanel.add(cercaPorNomButton);
    topPanel.add(fechaLabel);
    topPanel.add(fechaField);
    topPanel.add(cercaPorFechaButton);
    topPanel.add(categoriaLabel);
    topPanel.add(categoriaComboBox);
    topPanel.add(cercaPorCategoriaButton);
    
    add(topPanel, BorderLayout.NORTH);
    String[] columnNames = {"Nom", "NIF", "Data de naixement", "Categoria", "Seleccionat"};
    model = new DefaultTableModel(columnNames, 0);
    taulaJugadors = new JTable(model) {
    @Override
    public Class<?> getColumnClass(int column) {
        return column == 4 ? Boolean.class : String.class; // La columna 4 es de tipo Boolean, el resto es String
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Ninguna celda será editable
    }
};

    taulaJugadors.setRowHeight(30); 
   
    JScrollPane scrollPane = new JScrollPane(taulaJugadors);
    add(scrollPane, BorderLayout.CENTER);
    JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 10, 10));
    esborraSeleccionat = new JButton("Esborra seleccionat");
    afegirJugador = new JButton("Afegir jugador");
    exportarJugadors = new JButton("Exportar jugadors");
    bottomPanel.add(esborraSeleccionat);
    bottomPanel.add(afegirJugador);
    bottomPanel.add(exportarJugadors);
    add(bottomPanel, BorderLayout.SOUTH);
}
    public void actualizarTabla(HashMap<Integer, Jugador> jugadores, HashMap<Integer, Categoria> cat) {
        model.setRowCount(0);

        jugadores.forEach((key, jugador) -> {
            jugador.asignarCategoria(cat);

            Object[] rowData = new Object[5];
            rowData[0] = jugador.getNomJugador();
            rowData[1] = jugador.getIdLegal();
            rowData[2] = jugador.getDataNaix();
            rowData[3] = jugador.getCat() != null ? jugador.getCat().getNom() : "No asignada";
            rowData[4] = Boolean.FALSE;
            model.addRow(rowData);
        });
    }

public void cargarCategorias(HashMap<Integer, Categoria> categorias) {
    categoriaComboBox.removeAllItems(); 
    categorias.forEach((id, categoria) -> {
        categoriaComboBox.addItem(categoria.getNom());
    });
}

    public void buscarPorNombre(HashMap<Integer, Jugador> jugadores) {
        String nombre = nombreField.getText().toLowerCase();
        HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
        
        for (Jugador jugador : jugadores.values()) {
            if (jugador.getNomJugador().toLowerCase().contains(nombre)) {
                jugadoresFiltrados.put(jugador.getId(), jugador);
            }
        }
        
        actualizarTabla(jugadoresFiltrados, new HashMap<>());
    }

    public void buscarPorFecha(HashMap<Integer, Jugador> jugadores) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = fechaField.getText();
            HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();

            for (Jugador jugador : jugadores.values()) {
                if (sdf.format(jugador.getDataNaix()).contains(fecha)) {
                    jugadoresFiltrados.put(jugador.getId(), jugador);
                }
            }
            
            actualizarTabla(jugadoresFiltrados, new HashMap<>());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha no válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarPorCategoria(HashMap<Integer, Jugador> jugadores) {
        String categoriaSeleccionada = (String) categoriaComboBox.getSelectedItem();
        HashMap<Integer, Jugador> jugadoresFiltrados = new HashMap<>();
        
        for (Jugador jugador : jugadores.values()) {
            if (jugador.getCat() != null && jugador.getCat().getNom().equals(categoriaSeleccionada)) {
                jugadoresFiltrados.put(jugador.getId(), jugador);
            }
        }
        
        actualizarTabla(jugadoresFiltrados, new HashMap<>());
    }


    public JButton getCercaPerNomButton() {
        return cercaPorNomButton;
    }

    public JButton getCercaPerFechaButton() {
        return cercaPorFechaButton;
    }

    public JButton getCercaPerCategoriaButton() {
        return cercaPorCategoriaButton;
    }

    public JTable getTaulaJugadors() {
        return taulaJugadors;
    }

    public JButton getEsborraSeleccionat() {
        return esborraSeleccionat;
    }

    public JButton getAfegirJugador() {
        return afegirJugador;
    }

    public JButton getExportarJugadors() {
        return exportarJugadors;
    }

    public JTextField getNifField() {
        return nifField;
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getFechaField() {
        return fechaField;
    }

    public JButton getCercaButton() {
        return cercaButton;
    }


    public DefaultTableModel getModel() {
        return model;
    }

    public JComboBox<String> getCategoriaComboBox() {
        return categoriaComboBox;
    }
    
}
