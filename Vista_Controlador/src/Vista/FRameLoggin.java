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
public class FRameLoggin extends JFrame{
    JLabel usu;
    JLabel cont;
    JTextField usuari;
    JPasswordField contrasenya;
    JEditorPane olvidao;
    
    public FRameLoggin(){
    JPanel panel = new JPanel();
    panel.setLayout(null); // Establecer el layout a null


    JLabel usu = new JLabel("Usuario:");
    JTextField usuari = new JTextField(15);
    JLabel cont = new JLabel("Contraseña:");
    JPasswordField contrasenya = new JPasswordField(15);
    JButton loginButton = new JButton("Iniciar sesión");

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


    JFrame frame = new JFrame("Login");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(540, 325, 400, 200);
    frame.setContentPane(panel);
    frame.setVisible(true);

    }

}
