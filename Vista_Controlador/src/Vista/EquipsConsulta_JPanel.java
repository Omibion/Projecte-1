package Vista;

import david.milaifontanals.org.Equip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import vista_controlador.Vista_Controlador;

public class EquipsConsulta_JPanel extends JPanel {
    JTable taulaEquips;
    JButton CreaEquips;
    JButton EditaEquip;
    JButton BorraEquip;
    JButton Desa;  // Nuevo botón
    JButton Cerca;
    JComboBox<String> Temporada;
    JComboBox<String> Categoria;
    private DefaultTableModel model;
    Vista_Controlador controlador;

    public EquipsConsulta_JPanel() {
        setLayout(new BorderLayout(10, 10));
 
        String[] columnNames = {"ID", "Nom", "Categoria", "Temporada", "Seleccionat"};
        model = new DefaultTableModel(columnNames, 0);
        
    
        taulaEquips = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 4 ? Boolean.class : String.class; 
            }
        };
        taulaEquips.getColumnModel().getColumn(0).setMinWidth(0);
        taulaEquips.getColumnModel().getColumn(0).setMaxWidth(0);
        taulaEquips.getColumnModel().getColumn(0).setWidth(0);

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

        JPanel bottomPanel = new JPanel(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        CreaEquips = new JButton("Crear Equip");
        EditaEquip = new JButton("Editar Equip");
        BorraEquip = new JButton("Borrar Equip");
        Desa = new JButton("Desa");  // Crear el botón Desa
        gbc.gridx = 0;
        gbc.weightx = 1.0;  
        bottomPanel.add(CreaEquips, gbc);

        gbc.gridx = 1;
        bottomPanel.add(EditaEquip, gbc);

        gbc.gridx = 2;
        bottomPanel.add(BorraEquip, gbc);

        gbc.gridx = 3;
        bottomPanel.add(Desa, gbc);  

        add(bottomPanel, BorderLayout.SOUTH);
        taulaEquips.setDefaultEditor(Object.class, null);
         taulaEquips.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  
                    int idEquip = obtenerIdEquipDobleClic(e);  
                   
                }
            }
        });
    }

     public int obtenerIdEquipDobleClic(MouseEvent e) {
        int filaSeleccionada = taulaEquips.rowAtPoint(e.getPoint());
        return (int) model.getValueAt(filaSeleccionada, 0);
    }
 

    // Métodos getters
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

    public JButton getDesa() {  
        return Desa;
    }

    public JTable getTaulaEquips() {
        return taulaEquips;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void actualizarTablaEquipos(HashMap<Integer, Equip> equipos) {
        model.setRowCount(0); 

        equipos.forEach((id, equipo) -> {
            Object[] rowData = new Object[5];
            rowData[0] = id; 
            rowData[1] = equipo.getNomEquip();
            rowData[2] = equipo.getCat().getNom();
            rowData[3] = equipo.getTemp().getAnyFi();
            rowData[4] = Boolean.FALSE; 
            model.addRow(rowData);
        });
    }

    public Integer getIdSeleccionado() {
        int selectedRow = taulaEquips.getSelectedRow();
        if (selectedRow != -1) {
         
            return (Integer) taulaEquips.getModel().getValueAt(selectedRow, 0);
        }
        return null; 
    }
}
