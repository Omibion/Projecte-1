/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author isard
 */
public class FramePrincipal extends JFrame{
    JButton temp;
    JButton equip;
    JButton cat;
    JButton jugadors;
    private JPanel panelCentro;
    Temporades_JPanel tjp = new Temporades_JPanel();
    public FramePrincipal() {
        JFrame fp = new JFrame();
        fp.setBounds(300, 100, 900, 700);
        fp.getContentPane().setLayout(new BorderLayout());
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
        menu.setBackground(new Color(200,200,200));
        temp = new JButton("Gestiona Temporades");
        equip = new JButton("Gestiona Équips");
        cat = new JButton("Gestiona Categories");
        jugadors = new JButton("Gestiona Jugadors");
        
        estiloBoton(temp);
        estiloBoton(equip);
        estiloBoton(cat);
        estiloBoton(jugadors);
        agregarEfectoHover(temp);
        agregarEfectoHover(equip);
        agregarEfectoHover(cat);
        agregarEfectoHover(jugadors);
        menu.add(Box.createVerticalStrut(30)); 
        menu.add(temp);
        menu.add(Box.createVerticalStrut(30)); 
        menu.add(equip);
        menu.add(Box.createVerticalStrut(30)); 
        menu.add(cat);
        menu.add(Box.createVerticalStrut(30)); 
        menu.add(jugadors);
        
        fp.add(menu,BorderLayout.WEST);
       
        fp.setVisible(true);
        
    }
    
     public void cambiarPanel(String nombrePanel) {
     
        panelCentro.removeAll();

     
        switch (nombrePanel) {
            case "Temporades":
               panelCentro.add(new JButton("Panel de Équips"));
                break;
            case "Équips":
                panelCentro.add(new JButton("Panel de Équips"));
                break;
            case "Categories":
                panelCentro.add(new JButton("Panel de Categories"));
                break;
            case "Jugadors":
                panelCentro.add(new JButton("Panel de Jugadors"));
                break;
        }

 
        panelCentro.revalidate();
        panelCentro.repaint();
    }


    public JButton getTempButton() {
        return temp;
    }

    public JButton getEquipButton() {
        return equip;
    }

    public JButton getCatButton() {
        return cat;
    }

    public JButton getJugadorsButton() {
        return jugadors;
    } private void estiloBoton(JButton boton) {
        boton.setBackground(new Color(50, 50, 50));
        boton.setForeground(Color.BLACK); 
        boton.setBorder(BorderFactory.createEmptyBorder()); 
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFont(new Font("Arial", Font.PLAIN, 18)); 
        boton.setHorizontalAlignment(SwingConstants.LEFT); 
        boton.setPreferredSize(new Dimension(200, 40)); 
      
    }
    private void agregarEfectoHover(JButton boton) {
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                
                boton.setBackground(new Color(150, 150, 150)); 
                boton.setForeground(Color.WHITE); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
               
                boton.setBackground(new Color(200, 200, 200)); 
                boton.setForeground(Color.BLACK); 
            }
        });
    }
}
