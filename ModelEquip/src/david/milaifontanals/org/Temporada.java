/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package david.milaifontanals.org;

import java.util.Date;

/**
 *
 * @author isard
 */
public class Temporada {
    int idTemp;
    Date anyIni;
    Date anyFi;

    public Temporada(int idTemp, Date anyIni, Date anyFi) {
        this.idTemp = idTemp;
        this.anyIni = anyIni;
        this.anyFi = anyFi;
    }

    
    
    public Temporada(Date anyIni, Date anyFi) {
        this.anyIni = anyIni;
        this.anyFi = anyFi;
    }

    public int getIdTemp() {
        return idTemp;
    }

    public void setIdTemp(int idTemp) {
        this.idTemp = idTemp;
    }

    public Date getAnyIni() {
        return anyIni;
    }

    public void setAnyIni(Date anyIni) {
        this.anyIni = anyIni;
    }

    public Date getAnyFi() {
        return anyFi;
    }

    public void setAnyFi(Date anyFi) {
        this.anyFi = anyFi;
    }
    
    
}
