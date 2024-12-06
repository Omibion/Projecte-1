package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Panel de consulta de equipos.
 */
public class EquipsConsulta_JPanel extends JPanel {

    JTable taulaEquips;
    JButton CreaEquips;
    JButton EditaEquip;
    JButton BorraEquip;
    JComboBox Temporada;
    JComboBox Categoria;

    public EquipsConsulta_JPanel() {
       
        setLayout(new BorderLayout());

    
        taulaEquips = new JTable(5, 3);
        CreaEquips = new JButton("Crear Equip");
        EditaEquip = new JButton("Editar Equip");
        BorraEquip = new JButton("Borrar Equip");
        
        add(new JScrollPane(taulaEquips), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        bottomPanel.add(CreaEquips); 
        bottomPanel.add(EditaEquip); 
        bottomPanel.add(BorraEquip); 
        add(bottomPanel, BorderLayout.SOUTH);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(Temporada, BorderLayout.WEST);
        topPanel.add(Categoria, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
    }

   
}
