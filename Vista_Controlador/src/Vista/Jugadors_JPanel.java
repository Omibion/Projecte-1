package Vista;

import david.milaifontanals.org.Jugador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import javax.swing.table.TableCellRenderer;
import java.util.List;

public class Jugadors_JPanel extends JPanel {
    private JTable taulaJugadors;
    private JButton consultaSeleccionat, esborraSeleccionat, afegirJugador, exportarJugadors;
    private JTextField nifField;
    private JButton cercaButton;
    private DefaultTableModel model;

    public Jugadors_JPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel nifLabel = new JLabel("NIF");
        nifField = new JTextField(15);
        cercaButton = new JButton("Cerca");
        topPanel.add(nifLabel);
        topPanel.add(nifField);
        topPanel.add(cercaButton);
        add(topPanel, BorderLayout.NORTH);
        String[] columnNames = {"Nom", "NIF", "Data de naixement","Categoria"};
        Object[][] data = {};       
        taulaJugadors = new JTable(model);
        taulaJugadors.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(taulaJugadors);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        consultaSeleccionat = new JButton("Consulta seleccionat");
        esborraSeleccionat = new JButton("Esborra seleccionat");
        afegirJugador = new JButton("Afegir jugador");
        exportarJugadors = new JButton("Exportar jugadors");

        bottomPanel.add(consultaSeleccionat);
       
        bottomPanel.add(esborraSeleccionat);
        bottomPanel.add(afegirJugador);
        bottomPanel.add(exportarJugadors);

        add(bottomPanel, BorderLayout.SOUTH);
    }

  
    public void actualizarTabla(HashMap<Integer, Jugador> jugadores) {
        model.setRowCount(0);  
         jugadores.forEach((key, jugador) -> {
        Object[] row = {jugador.getNomJugador(), jugador.getIdLegal(), jugador.getDataNaix(), jugador.getCat()};
        model.addRow(row);
    });
    }
}
