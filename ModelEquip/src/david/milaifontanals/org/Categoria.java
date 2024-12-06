/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package david.milaifontanals.org;

/**
 *
 * @author isard
 */
public class Categoria {
    int id;
    String nom;
    int edatMax;
    int edatMin;

    public Categoria(String nom, int edatMax, int edatMin) {
        this.nom = nom;
        this.edatMax = edatMax;
        this.edatMin = edatMin;
    }

    public Categoria(int id, String nom, int edatMax, int edatMin) {
        this.id = id;
        this.nom = nom;
        this.edatMax = edatMax;
        this.edatMin = edatMin;
    }

    public int getId() {
        return id;
    }
    
    
    public Categoria(String nom, int edatMin) {
        this.nom = nom;
        this.edatMin = edatMin;
    }

    public String getNom() {
        return nom;
    }

    
    public int getEdatMax() {
        return edatMax;
    }

    
    public int getEdatMin() {
        return edatMin;
    }

    
}
