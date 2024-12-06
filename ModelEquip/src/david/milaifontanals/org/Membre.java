/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package david.milaifontanals.org;

import java.util.ArrayList;

/**
 *
 * @author isard
 */
public class Membre {
   Jugador jug;
   Equip eq;
   char titular;

    public Membre(Jugador jug, Equip eq, char titular) {
        this.jug = jug;
        this.eq = eq;
        this.titular = titular;
    }

    public Jugador getJug() {
        return jug;
    }

    public void setJug(Jugador jug) {
        this.jug = jug;
    }

    public Equip getEq() {
        return eq;
    }

    public void setEq(Equip eq) {
        this.eq = eq;
    }

    public char getTitular() {
        return titular;
    }

    public void setTitular(char titular) {
        this.titular = titular;
    }
   
   
}
