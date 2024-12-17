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
    JButton BorraEquip,Cerca;
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
        Cerca = new JButton("Cerca");
        topPanel.add(new JLabel("Temporada:"));
        topPanel.add(Temporada);
        topPanel.add(new JLabel("Categoria:"));
        topPanel.add(Categoria);
        topPanel.add(Cerca);
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

    public JComboBox<String> getTemporada() {
        return Temporada;
    }

    public JComboBox<String> getCategoria() {
        return Categoria;
    }

    public JButton getCreaEquips() {
        return CreaEquips;
    }

    public JButton getEditaEquip() {
        return EditaEquip;
    }

    public JButton getBorraEquip() {
        return BorraEquip;
    }

    public JButton getCerca() {
        return Cerca;
    }

    public JTable getTaulaEquips() {
        return taulaEquips;
    }

    public DefaultTableModel getModel() {
        return model;
    }
     
    public void actualizarTablaEquipos(HashMap<Integer,Equip> equipos) {
       
        model.setRowCount(0); 
        
       
         equipos.forEach((key, equipo) -> { 
        Object[] rowData = new Object[4];
        rowData[0] = equipo.getNomEquip();
        rowData[1] = equipo.getCat().getNom();
        rowData[2] = equipo.getTemp().getAnyFi(); 
        rowData[3] = Boolean.FALSE;
             
        model.addRow(rowData);  
    });
    }
    public void actualizarTabla(HashMap<Integer, Equip> equipos) {
    DefaultTableModel model = (DefaultTableModel) taulaEquips.getModel();
    model.setRowCount(0); 
    
    for (Equip equip : equipos.values()) {
        model.addRow(new Object[]{equip.getNomEquip(), equip.getCat().getNom(), equip.getTemp().getAnyIni()});
    }
}

}
