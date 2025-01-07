/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;

/**
 *
 * @author isard
 */
public class FRameLoggin extends JFrame {
   
    private JLabel usu;
    private JLabel cont;
    private JTextField usuari;
    private JPasswordField contrasenya;
    private JButton loginButton;

    public FRameLoggin() {
 
        JPanel panel = new JPanel();
        panel.setLayout(null); 

        usu = new JLabel("Usuari:");
        usuari = new JTextField(15);
        cont = new JLabel("Contraseny:");
        contrasenya = new JPasswordField(15);
        loginButton = new JButton("Iniciar sesió");

        usu.setBounds(50, 30, 80, 25);
        usuari.setBounds(150, 30, 150, 25);
        cont.setBounds(50, 70, 80, 25);
        contrasenya.setBounds(150, 70, 150, 25);
        loginButton.setBounds(150, 110, 150, 30);

        panel.add(usu);
        panel.add(usuari);
        panel.add(cont);
        panel.add(contrasenya);
        panel.add(loginButton);

   
        this.setTitle("Login");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(540, 325, 400, 200);
        this.setContentPane(panel);
        this.setVisible(true);
    }
    public JTextField getUsuari() {
        return usuari;
    }

    public JPasswordField getContrasenya() {
        return contrasenya;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
