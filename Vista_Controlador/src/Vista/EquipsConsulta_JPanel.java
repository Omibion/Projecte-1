package Vista;

import david.milaifontanals.org.Equip;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class EquipsConsulta_JPanel extends JPanel {
    JTable taulaEquips;
    JButton CreaEquips;
    JButton EditaEquip;
    JButton BorraEquip;
    JComboBox<String> Temporada;
    JComboBox<String> Categoria;
    private DefaultTableModel model;
    public EquipsConsulta_JPanel() {
        setLayout(new BorderLayout(10, 10));
        
        String[] columnNames = {"Nom", "Categoria", "Temporada", "Seleccionat"};
        model = new DefaultTableModel(columnNames, 0);
        taulaEquips = new JTable(model) {@Override
        public Class<?> getColumnClass(int column) {
            return column == 3 ? Boolean.class : String.class;
        }
    };
        JScrollPane scroll = new JScrollPane(taulaEquips);
        add(scroll, BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Temporada = new JComboBox<>();
        Categoria = new JComboBox<>();
        topPanel.add(new JLabel("Temporada:"));
        topPanel.add(Temporada);
        topPanel.add(new JLabel("Categoria:"));
        topPanel.add(Categoria);
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        CreaEquips = new JButton("Crear Equip");
        EditaEquip = new JButton("Editar Equip");
        BorraEquip = new JButton("Borrar Equip");
        bottomPanel.add(CreaEquips);
        bottomPanel.add(EditaEquip);
        bottomPanel.add(BorraEquip);
        add(bottomPanel, BorderLayout.SOUTH);
       
    }
     
    public void actualizarTablaEquipos(ArrayList<Equip> equipos) {
       
        model.setRowCount(0); 
        
       
        for (Equip equipo : equipos) {
            Object[] rowData = new Object[4];
            rowData[0] = equipo.getNomEquip();
            rowData[1] = equipo.getCat(); 
            rowData[2] = equipo.getTemp(); 
            rowData[3] = Boolean.FALSE; 

            model.addRow(rowData);
        }
    }
    public void actualizarTabla(HashMap<Integer, Equip> equipos) {
    DefaultTableModel model = (DefaultTableModel) taulaEquips.getModel();
    model.setRowCount(0); // Limpiar la tabla
    
    for (Equip equip : equipos.values()) {
        model.addRow(new Object[]{equip.getNomEquip(), equip.getCat(), equip.getTemp()});
    }
}

}
