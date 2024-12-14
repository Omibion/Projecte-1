package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FramePrincipal extends JFrame {
    public JButton temp;
    public JButton equip;
    public JButton jugadors;
    public JPanel panelCentro;

    public FramePrincipal() {
        this.setBounds(300, 100, 900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(200, 200, 200));
        temp = new JButton("Gestiona Temporades");
        equip = new JButton("Gestiona Équips");
        jugadors = new JButton("Gestiona Jugadors");
        estiloBoton(temp);
        estiloBoton(equip);
        estiloBoton(jugadors);
         menu.add(Box.createVerticalStrut(30));
        menu.add(equip);
        menu.add(Box.createVerticalStrut(30));
        menu.add(temp);
           
        menu.add(Box.createVerticalStrut(30));
        menu.add(jugadors);
        this.add(menu, BorderLayout.WEST);
        panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout()); 
        this.add(panelCentro, BorderLayout.CENTER);
    }

    private void estiloBoton(JButton boton) {
        boton.setBackground(new Color(50, 50, 50));
        boton.setForeground(Color.BLACK);
        boton.setBorder(BorderFactory.createEmptyBorder());
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFont(new Font("Arial", Font.PLAIN, 18));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setPreferredSize(new Dimension(200, 40));
    }

    public JPanel getPanelCentro() {
        return panelCentro;
    }
}
