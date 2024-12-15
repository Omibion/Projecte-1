/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package david.milaifontanals.org;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author isard
 */
public class Jugador {
    int id;
    String nomJugador;
    String cognoms;
    char sexe;
    Date dataNaix;
    String idLegal;
    String iban;
    Date anyFiRevisioMedica;
    String adreça;
    String foto;
    Categoria cat;

    public Jugador(int id, String nom, String cognoms, char sexe, Date dataNaix, String idLegal, String iban, Date anyFiRevisioMedica, String adreça, String foto) {
        this.id = id;
        this.nomJugador = nom;
        this.cognoms = cognoms;
        this.sexe = sexe;
        this.dataNaix = dataNaix;
        this.idLegal = idLegal;
        this.iban = iban;
        this.anyFiRevisioMedica = anyFiRevisioMedica;
        this.adreça = adreça;
        this.foto = foto;
        
        
    }

    public Jugador(int id, String nom, String cognoms, char sexe, Date dataNaix, String idLegal, String iban, String adreça, String foto) {
        this.id = id;
        this.nomJugador = nom;
        this.cognoms = cognoms;
        this.sexe = sexe;
        this.dataNaix = dataNaix;
        this.idLegal = idLegal;
        this.iban = iban;
        this.adreça = adreça;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomJugador() {
        return nomJugador;
    }

    public void setNomJugador(String nom) {
        this.nomJugador = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public Date getDataNaix() {
        return dataNaix;
    }

    public void setDataNaix(Date dataNaix) {
        this.dataNaix = dataNaix;
    }

    public String getIdLegal() {
        return idLegal;
    }

    public void setIdLegal(String idLegal) {
        this.idLegal = idLegal;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Date getAnyFiRevisioMedica() {
        return anyFiRevisioMedica;
    }

    public void setAnyFiRevisioMedica(Date anyFiRevisioMedica) {
        this.anyFiRevisioMedica = anyFiRevisioMedica;
    }

    public String getAdreça() {
        return adreça;
    }

    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }
    public int calcularEdad(Date fechaNacimiento) {
    Calendar fechaActual = Calendar.getInstance();
    Calendar fechaNac = Calendar.getInstance();
    fechaNac.setTime(fechaNacimiento);

    int edad = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
    if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaNac.get(Calendar.DAY_OF_YEAR)) {
        edad--;
    }
    return edad;
}
    public void asignarCategoria(ArrayList<Categoria> categorias) {
    int edad = calcularEdad(this.dataNaix); 

    for (Categoria categoria : categorias) {
        if (edad >= categoria.getEdatMin() && edad <= categoria.getEdatMax()) {
            this.cat = categoria; 
            break;
        }
    }

    if (this.cat == null) {
        System.out.println("No se encontró una categoría para la edad: " + edad);
    }
}
}
