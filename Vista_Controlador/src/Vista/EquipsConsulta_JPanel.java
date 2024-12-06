/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author isard
 */
public class EquipsConsulta_JPanel extends JPanel{
    
    JTable taulaEquips;
    JButton CreaEquips;
    JButton MostraEquip;
   JComboBox Temporada;
   JComboBox Categoria;

    public EquipsConsulta_JPanel() {
       
        setLayout(new BorderLayout());
        taulaEquips = new JTable(5, 3); 
        CreaEquips = new JButton("Crear Equip");
        MostraEquip = new JButton("Mostrar Equip");
        add(new JScrollPane(taulaEquips), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(CreaEquips, BorderLayout.WEST);
        bottomPanel.add(MostraEquip, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(Temporada, BorderLayout.WEST);
        topPanel.add(Categoria, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
    }
   
   
}
