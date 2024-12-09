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
    public JButton cat;
    public JButton jugadors;
    public JPanel panelCentro;

    public FramePrincipal() {
        // Configurar la ventana principal
        this.setBounds(300, 100, 900, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        // Configurar el menú lateral
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(200, 200, 200));

        // Crear botones
        temp = new JButton("Gestiona Temporades");
        equip = new JButton("Gestiona Équips");
        cat = new JButton("Gestiona Categories");
        jugadors = new JButton("Gestiona Jugadors");

        // Aplicar estilo y efectos a los botones
        estiloBoton(temp);
        estiloBoton(equip);
        estiloBoton(cat);
        estiloBoton(jugadors);

        menu.add(Box.createVerticalStrut(30));
        menu.add(temp);
        menu.add(Box.createVerticalStrut(30));
        menu.add(equip);
        menu.add(Box.createVerticalStrut(30));
        menu.add(cat);
        menu.add(Box.createVerticalStrut(30));
        menu.add(jugadors);

        // Agregar el menú al lado izquierdo
        this.add(menu, BorderLayout.WEST);

        // Configurar el panel central
        panelCentro = new JPanel();
        panelCentro.setLayout(new BorderLayout()); // Diseño flexible para el centro
        this.add(panelCentro, BorderLayout.CENTER);
    }

    // Métodos auxiliares
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
