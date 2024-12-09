package Vista;

import javax.swing.*;
import java.awt.*;

public class EquipsConsulta_JPanel extends JPanel {
    JTable taulaEquips;
    JButton CreaEquips;
    JButton EditaEquip;
    JButton BorraEquip;
    JComboBox<String> Temporada;
    JComboBox<String> Categoria;

    public EquipsConsulta_JPanel() {
        setLayout(new BorderLayout(10, 10));
        taulaEquips = new JTable(10, 5);
        JScrollPane scroll = new JScrollPane(taulaEquips);
        add(scroll, BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Temporada = new JComboBox<>(new String[]{"2021", "2022", "2023", "2024"});
        Categoria = new JComboBox<>(new String[]{"Senior", "Junior", "Infantil"});
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
}
