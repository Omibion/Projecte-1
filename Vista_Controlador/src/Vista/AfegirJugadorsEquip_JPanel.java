/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import david.milaifontanals.org.Equip;
import david.milaifontanals.org.Jugador;
import david.milaifontanals.org.Membre;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author isard
 */
public class AfegirJugadorsEquip_JPanel extends JPanel{
     private JTable taulaJugadors;
    private JTextField nifField;
    private JButton cercaButton, afegeixSeleccionats, esborraSeleccionats, tornaButton;
    private DefaultTableModel model;

    public AfegirJugadorsEquip_JPanel(Equip eq) {
        setLayout(new BorderLayout(10, 10));
        SimpleDateFormat sdf=new SimpleDateFormat("yy");
        String temporada = sdf.format(eq.getTemp().getAnyIni())+"-"+sdf.format(eq.getTemp().getAnyFi());
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel temporadaLabel = new JLabel(temporada);
        JLabel categoriaLabel = new JLabel(eq.getCat().getNom());
        String tipus = null;
        if(eq.getTipus()=='M'){
            tipus="Mixt";
        }
        else if(eq.getTipus()=='H'){
            tipus="Massculí";
        }
        else{
            tipus ="Femení";
        }
        JLabel sexeLabel = new JLabel(tipus);
        JLabel nifLabel = new JLabel("NIF");
        nifField = new JTextField(15);
        cercaButton = new JButton("Cerca");

        infoPanel.add(temporadaLabel);
        infoPanel.add(categoriaLabel);
        infoPanel.add(sexeLabel);
        infoPanel.add(nifLabel);
        infoPanel.add(nifField);
        infoPanel.add(cercaButton);

        topPanel.add(infoPanel);

        add(topPanel, BorderLayout.NORTH);
        String[] columnNames = {"id","Nom", "Data de naixement", "NIF","Categoria", "Titular", "Afegir"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5 || column == 6) {
                    return Boolean.class; 
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6; 
            }
        };

        taulaJugadors = new JTable(model);
        taulaJugadors.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(taulaJugadors);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        afegeixSeleccionats = new JButton("Afegeix seleccionats");
        esborraSeleccionats = new JButton("Esborra seleccionats");
        tornaButton = new JButton("Torna");

        bottomPanel.add(afegeixSeleccionats);
        bottomPanel.add(esborraSeleccionats);
        bottomPanel.add(tornaButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    public void cargarJugadors(Object[][] datos) {
        model.setRowCount(0); 
        for (Object[] fila : datos) {
            model.addRow(fila);
        }
    }

    
    public JTextField getNifField() {
        return nifField;
    }

    public JButton getCercaButton() {
        return cercaButton;
    }

    public JButton getAfegeixSeleccionats() {
        return afegeixSeleccionats;
    }

    public JButton getEsborraSeleccionats() {
        return esborraSeleccionats;
    }

    public JButton getTornaButton() {
        return tornaButton;
    }

    public JTable getTaulaJugadors() {
        return taulaJugadors;
    }
   public boolean jugadorYaEnEquipo(Membre membre, int idEquipoSeleccionado) {
    
    return membre.getEq().getIdEq() == idEquipoSeleccionado;
}

public void actualizarTabla(HashMap<Integer, Jugador> jugadoresFiltrados, int idEquipoSeleccionado, ArrayList<Membre> membresDelEquipo) {

    model.setRowCount(0);

    for (Jugador jugador : jugadoresFiltrados.values()) {
      
        boolean estaEnEquipo = false;
        for (Membre membre : membresDelEquipo) {
            if (membre.getJug().getId() == jugador.getId() && jugadorYaEnEquipo(membre, idEquipoSeleccionado)) {
                estaEnEquipo = true;
                break;
            }
        }

        
        if (!estaEnEquipo) {
            Object[] rowData = new Object[7];
            rowData[0] = jugador.getId();
            rowData[1] = jugador.getNomJugador();
            rowData[2] = jugador.getDataNaix();
            rowData[3] = jugador.getIdLegal();
            rowData[4] = jugador.getCat().getNom();
            rowData[5] = false;
            rowData[6] = false;

            model.addRow(rowData);
        }
    }


    if (taulaJugadors.getColumnModel().getColumnCount() > 0) {
        taulaJugadors.getColumnModel().getColumn(0).setMinWidth(0);
        taulaJugadors.getColumnModel().getColumn(0).setMaxWidth(0);
        taulaJugadors.getColumnModel().getColumn(0).setWidth(0);
    }
}
public ArrayList<Jugador> obtenerMiembrosSeleccionados(JTable tablaMiembros, HashMap<Integer, Jugador> mapaMiembros) {
    ArrayList<Jugador> miembrosSeleccionados = new ArrayList<>();
    TableModel modelo = tablaMiembros.getModel();
    for (int i = 0; i < modelo.getRowCount(); i++) {
        Boolean seleccionado = (Boolean) modelo.getValueAt(i, 6);
        if (Boolean.TRUE.equals(seleccionado)) {
            Object idObj = modelo.getValueAt(i, 0);
            if (idObj instanceof Integer) {
                Integer idMiembro = (Integer) idObj;
                if (mapaMiembros.containsKey(idMiembro)) {
                    miembrosSeleccionados.add(mapaMiembros.get(idMiembro));
                }
            }
        }
    }

    return miembrosSeleccionados;
}



   
}

