package Vista;

import david.milaifontanals.org.Categoria;
import david.milaifontanals.org.Jugador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Jugadors_JPanel extends JPanel {
    private JTable taulaJugadors;
    private JButton esborraSeleccionat, afegirJugador, exportarJugadors;
    private JTextField nifField;
    private JButton cercaButton;
    private DefaultTableModel model;

    public Jugadors_JPanel() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel nifLabel = new JLabel("NIF");
        nifField = new JTextField(15);
        cercaButton = new JButton("Cerca");
        topPanel.add(nifLabel);
        topPanel.add(nifField);
        topPanel.add(cercaButton);
        add(topPanel, BorderLayout.NORTH);
        String[] columnNames = {"Nom", "NIF", "Data de naixement", "Categoria", "Seleccionat"};
        model = new DefaultTableModel(columnNames, 0);
        taulaJugadors = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 4 ? Boolean.class : String.class;
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
    public void actualizarTabla(HashMap<Integer, Jugador> jugadores, ArrayList<Categoria> cat) {

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
}
