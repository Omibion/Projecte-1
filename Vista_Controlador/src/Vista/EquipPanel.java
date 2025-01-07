package Vista;

import david.milaifontanals.org.Equip;
import david.milaifontanals.org.Jugador;
import david.milaifontanals.org.Membre;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class EquipPanel extends JPanel {
    private Equip equipo;
    private JTable tabla;
    private JButton botonAgregar, botonEliminar, botonVolver,botonDesa,botonEdita;

     public EquipPanel(Equip equipo) {
        this.equipo = equipo;

        setLayout(new BorderLayout());
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String temporada = sdf.format(equipo.getTemp().getAnyIni()) + "-" + sdf.format(equipo.getTemp().getAnyFi());
        JLabel nombreLabel = new JLabel("Nom de l'equip: " + equipo.getNomEquip());
        JLabel categoriaLabel = new JLabel("Categoría: " + equipo.getCat().getNom());
        JLabel temporadaLabel = new JLabel("Temporada: " + temporada);

        panelSuperior.add(nombreLabel);
        panelSuperior.add(categoriaLabel);
        panelSuperior.add(temporadaLabel);

        add(panelSuperior, BorderLayout.NORTH);

        String[] nombresColumnas = {"Nom", "Data de naixement", "NIF", "Titular", "Selecciona"};
        DefaultTableModel modeloTabla = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public Class<?> getColumnClass(int indiceColumna) {
                return (indiceColumna == 3 || indiceColumna == 4) ? Boolean.class : String.class;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setPreferredScrollableViewportSize(new Dimension(600, 200));
        tabla.setFillsViewportHeight(true);

        JScrollPane panelDesplazamiento = new JScrollPane(tabla);
        add(panelDesplazamiento, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonAgregar = new JButton("Afegir");
        botonEliminar = new JButton("Eliminar seleccionats");
        botonEdita = new JButton("Edita Equip");
        botonVolver = new JButton("Torna");
        botonDesa = new JButton("Desa");

        panelInferior.add(botonAgregar);
        panelInferior.add(botonEliminar);
        panelInferior.add(botonEdita); 
        panelInferior.add(botonVolver);
        panelInferior.add(botonDesa);

        add(panelInferior, BorderLayout.SOUTH);
    }

    public JButton getBotonEdita() {
        return botonEdita;
    }
     

    public JTable getTabla() {
        return tabla;
    }

    public JButton getBotonAgregar() {
        return botonAgregar;
    }

    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    public JButton getBotonVolver() {
        return botonVolver;
    }

    public JButton getBotonDesa() {
        return botonDesa;
    }
    
    
    public ArrayList<Membre> cargarJugadores(int idEquipo, HashMap<String, Membre> hmmem) {
    DefaultTableModel model = (DefaultTableModel) tabla.getModel();
    model.setRowCount(0); 
    ArrayList<Membre> membres = new ArrayList<>();

    if (model.getColumnCount() < 6) {
        model.addColumn("ID_Jugador"); 
        tabla.getColumnModel().getColumn(5).setMinWidth(0);
        tabla.getColumnModel().getColumn(5).setMaxWidth(0);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
    }
    
    for (Membre membre : hmmem.values()) {
        if (membre.getEq().getIdEq() == idEquipo) {
            Jugador jugador = membre.getJug();
            membres.add(membre);
            model.addRow(new Object[] {
                jugador.getNomJugador(),
                new SimpleDateFormat("dd/MM/yyyy").format(jugador.getDataNaix()),
                jugador.getIdLegal(),
                membre.getTitular() == 'S',
                false,
                jugador.getId()
            });
        }
    }
    for (int i = 0; i < model.getRowCount(); i++) {
    boolean esTitular = (boolean) model.getValueAt(i, 3);
    int idJugador = (int) model.getValueAt(i, 5); 

    Membre membre = hmmem.get(idJugador + "" + idEquipo); 
    if (membre != null) {
        membre.setTitular(esTitular ? 'S' : 'N');
    }
}

 
    return membres; 
}

public ArrayList<Jugador> obtenerMiembrosSeleccionados(JTable tablaJugadores, HashMap<Integer, Jugador> hmjug) {
    ArrayList<Jugador> jugadoresSeleccionados = new ArrayList<>();
    
   
    tablaJugadores.editingStopped(null);
    
    for (int i = 0; i < tablaJugadores.getRowCount(); i++) {
        Object valorCelda = tablaJugadores.getValueAt(i, 4); 
        
        if (valorCelda instanceof Boolean && (Boolean) valorCelda) {
            Object idJugadorObj = tablaJugadores.getValueAt(i, 5); 
            
            if (idJugadorObj instanceof Integer) {
                int idJugador = (Integer) idJugadorObj;
                if (hmjug.containsKey(idJugador)) {
                    jugadoresSeleccionados.add(hmjug.get(idJugador));
                }
            } else if (idJugadorObj instanceof String) {
               
                    int idJugador = Integer.parseInt((String) idJugadorObj);
                    if (hmjug.containsKey(idJugador)) {
                        jugadoresSeleccionados.add(hmjug.get(idJugador));
                    }
                } 
            } 
        } 
    return jugadoresSeleccionados;
}
public HashMap<String,Membre> obtenerMiembrosTitulares(JTable tablaJugadores, HashMap<Integer, Jugador> hmjug,HashMap<Integer,Equip>hmeqp,int idEquip) {
    HashMap<String,Membre> jugadoresSeleccionados = new HashMap<>();
    
   
    tablaJugadores.editingStopped(null);
    
    for (int i = 0; i < tablaJugadores.getRowCount(); i++) {
        Object valorCelda = tablaJugadores.getValueAt(i, 3); 
        
        if (valorCelda instanceof Boolean&& (Boolean) valorCelda) {
            Object idJugadorObj = tablaJugadores.getValueAt(i, 5); 
                    Membre mem= new Membre(hmjug.get((Integer)idJugadorObj),hmeqp.get(idEquip),'S');
                    jugadoresSeleccionados.put(""+mem.getJug().getId()+mem.getEq().getIdEq(),mem);
                    mem=null;
                }
        else{
            Object idJugadorObj = tablaJugadores.getValueAt(i, 5); 
                    Membre    mem= new Membre(hmjug.get((Integer)idJugadorObj),hmeqp.get(idEquip),'N');
                    jugadoresSeleccionados.put(""+mem.getJug().getId()+mem.getEq().getIdEq(),mem);
                    mem=null;
        }
            
    } 
        
    return jugadoresSeleccionados;
}



}
