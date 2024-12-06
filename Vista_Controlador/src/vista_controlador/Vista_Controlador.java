/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vista_controlador;

import Vista.FRameLoggin;
import Vista.FramePrincipal;
import javax.swing.WindowConstants;

/**
 *
 * @author isard
 */
public class Vista_Controlador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FRameLoggin fl = new FRameLoggin();
        FramePrincipal fp=new FramePrincipal();
        fl.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
}

