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

public class EquipPanel extends JPanel {
    private Equip equipo; // Almacenar el equipo
    private JTable tabla;
    private JButton botonAgregar, botonEliminar, botonVolver;

    public EquipPanel(Equip equipo) {
        this.equipo = equipo;

        setLayout(new BorderLayout());
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String temporada = sdf.format(equipo.getTemp().getAnyIni()) + "-" + sdf.format(equipo.getTemp().getAnyFi());
        JLabel nombreLabel = new JLabel("Nombre del equipo: " + equipo.getNomEquip());
        JLabel categoriaLabel = new JLabel("Categoría: " + equipo.getCat().getNom());
        JLabel temporadaLabel = new JLabel("Temporada: " + temporada);

        panelSuperior.add(nombreLabel);
        panelSuperior.add(categoriaLabel);
        panelSuperior.add(temporadaLabel);

        add(panelSuperior, BorderLayout.NORTH);

        String[] nombresColumnas = {"Nombre", "Fecha de nacimiento", "NIF", "Titular"};
        DefaultTableModel modeloTabla = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public Class<?> getColumnClass(int indiceColumna) {
                return (indiceColumna == 3) ? Boolean.class : String.class;
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
        botonVolver = new JButton("Torna");

        panelInferior.add(botonAgregar);
        panelInferior.add(botonEliminar);
        panelInferior.add(botonVolver);

        add(panelInferior, BorderLayout.SOUTH);
        
    }

   public EquipPanel() {
        botonVolver = new JButton("Volver");
        // Agregar el botón al layout de la clase EquipPanel
        this.add(botonVolver);
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
    

    public void cargarJugadores(int idEquipo, HashMap<String, Membre> hmmem) {
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0); // Limpia cualquier dato previo

        
        for (Membre membre : hmmem.values()) {
            if (membre.getEq().getIdEq() == idEquipo) {
                Jugador jugador = membre.getJug();
                model.addRow(new Object[] {
                    jugador.getNomJugador(),
                    new SimpleDateFormat("dd/MM/yyyy").format(jugador.getDataNaix()),
                    jugador.getIdLegal(),
                    membre.getTitular() == 'S'
                });
            }
        }
    }
}
