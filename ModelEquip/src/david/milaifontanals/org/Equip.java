/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package david.milaifontanals.org;

import java.util.HashMap;

/**
 *
 * @author isard
 */
public class Equip {
    int idEq;
    String nomEquip;
    char tipus;
    Categoria cat;
    Temporada temp;

    public Equip(int idEq, String nomEquip, char tipus, Categoria cat, Temporada temp) {
        this.idEq = idEq;
        this.nomEquip = nomEquip;
        this.tipus = tipus;
        this.cat = cat;
        this.temp = temp;
    }
    
    

    public Equip(String nomEquip, char tipus, Categoria cat, Temporada temp) {
        this.nomEquip = nomEquip;
        this.tipus = tipus;
        this.cat = cat;
        this.temp = temp;
    }

    public int getIdEq() {
        return idEq;
    }

    public void setIdEq(int idEq) {
        this.idEq = idEq;
    }

    public String getNomEquip() {
        return nomEquip;
    }

    public void setNomEquip(String nomEquip) {
        this.nomEquip = nomEquip;
    }

    public char getTipus() {
        return tipus;
    }

    public void setTipus(char tipus) {
        this.tipus = tipus;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    public Temporada getTemp() {
        return temp;
    }

    public void setTemp(Temporada temp) {
        this.temp = temp;
    }
    
    
    
}
