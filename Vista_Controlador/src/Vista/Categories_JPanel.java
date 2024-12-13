package Vista;

import javax.swing.*;
import java.awt.*;

public class Categories_JPanel extends JPanel {
    JTable taulaCategories;
    JButton CreaCategoria;
    JButton EditaCategoria;
    JButton BorraCategoria;
    JComboBox<String> Temporada;

    public Categories_JPanel() {
        setLayout(new BorderLayout(10, 10));
        taulaCategories = new JTable(10, 3); // 10 filas, 3 columnas
        JScrollPane scroll = new JScrollPane(taulaCategories);
        add(scroll, BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));     
        topPanel.add(new JLabel("Temporada:"));
        topPanel.add(Temporada);
        add(topPanel, BorderLayout.NORTH);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        CreaCategoria = new JButton("Crear Categoria");
        EditaCategoria = new JButton("Editar Categoria");
        BorraCategoria = new JButton("Borrar Categoria");
        bottomPanel.add(CreaCategoria);
        bottomPanel.add(EditaCategoria);
        bottomPanel.add(BorraCategoria);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
