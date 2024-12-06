/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author isard
 */
public class Temporades_JPanel extends JPanel{
    JLabel text;
    JComboBox anyini;
    JTable taula;
    JButton afegir;
    JButton esborrar;

    public Temporades_JPanel() {
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        text =  new JLabel("Afegir i eliminar temporades:");
        gbc.gridx = 0;
        gbc.gridy=0;
        gbc.gridheight=2;
        jp.add(text);
        
        anyini = new JComboBox();
        gbc.gridx=0;
        gbc.gridy=1;
        jp.add(anyini);
        
        taula = new JTable();
        gbc.gridx= 0;
        gbc.gridy=2;
        gbc.gridheight=2;
        jp.add(taula);
        
        afegir=new JButton("Afegir temporada");
        gbc.gridx=1;
        gbc.gridy=1;
        jp.add(afegir);
        
        esborrar=new JButton("Eliminar temporada");
        gbc.gridx=1;
        gbc.gridy=2;
        jp.add(esborrar);
        //Falta cargar los datos de la taula, ya seguire el lunes, pos ya es viernes, hahahahaha, no he hecho na...
    }
    
    
}
