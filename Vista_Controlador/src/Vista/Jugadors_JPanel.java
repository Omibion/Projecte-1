package Vista;

import david.milaifontanals.org.Jugador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableCellRenderer;

public class Jugadors_JPanel extends JPanel {
    private JTable taulaJugadors;
    private JButton consultaSeleccionat, esborraSeleccionat, afegirJugador, exportarJugadors;
    private JTextField nifField;
    private JButton cercaButton;

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
        String[] columnNames = {"Nom", "NIF", "Data de naixement","Categoria", "Seleccionar"};
        Object[][] data = {}; 
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? JRadioButton.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        taulaJugadors = new JTable(model);
        taulaJugadors.setRowHeight(30);
        taulaJugadors.getColumnModel().getColumn(3).setCellRenderer(new RadioButtonRenderer());
        taulaJugadors.getColumnModel().getColumn(3).setCellEditor(new RadioButtonEditor(new JRadioButton()));

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

   
     public void actualizarTabla(List<Jugador> jugadores) {
        model.setRowCount(0);  // Limpiar la tabla antes de llenarla
        for (Jugador jugador : jugadores) {
            Object[] row = {jugador.getNomJugador(), jugador.getIdLegal(), jugador.getDataNaix(), jugador.getCategoria()};
            model.addRow(row);
        }
    }
}
