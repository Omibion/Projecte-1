package Vista;

import david.milaifontanals.org.Equip;
import david.milaifontanals.org.Categoria;
import david.milaifontanals.org.Temporada;

import javax.swing.*;
import java.awt.*;
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

    // HashMaps para almacenar las categorías y temporadas (asumiendo que ya las tienes)
    private HashMap<Integer, Categoria> hmcat;
    private HashMap<Integer, Temporada> hmtemp;

    public EquipsConsulta_JPanel(HashMap<Integer, Categoria> hmcat, HashMap<Integer, Temporada> hmtemp) {
        this.hmcat = hmcat;
        this.hmtemp = hmtemp;

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

        llenarCategorias();

        llenarTemporadas();
    }

   
    private void llenarCategorias() {
        Categoria.removeAllItems(); 
        for (Categoria cat : hmcat.values()) {
            Categoria.addItem(cat.getNom()); 
        }
    }

    
    private void llenarTemporadas() {
        Temporada.removeAllItems(); 
        for (Temporada temp : hmtemp.values()) {
            String temporadaFormateada = temp.getAnyIni() + "-" + temp.getAnyFi(); 
            Temporada.addItem(temporadaFormateada);
        }
    }

    public void actualizarTablaEquipos(HashMap<Integer, Equip> equipos) {
        model.setRowCount(0); 

        equipos.forEach((key, equipo) -> {
            Object[] rowData = new Object[4];
            rowData[0] = equipo.getNomEquip();
            rowData[1] = equipo.getCat().getNom();
            rowData[2] = equipo.getTemp().getAnyFi(); 
            rowData[3] = Boolean.FALSE;
            System.out.println("" + equipo.getNomEquip());
            model.addRow(rowData);
        });
    }
}
