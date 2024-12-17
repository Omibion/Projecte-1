/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package david.milaifontanals.org;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author isard
 */
public interface Interficie_persistencia {
    public String encriptar_contrasenya(String contrasenya) throws gestorEquipsException;
    
    public boolean afegir_usuari(String nom, String password, String Loggin) throws gestorEquipsException; 
    
    public boolean afegir_categoria(String nom, int edatMin, int edatMax) throws gestorEquipsException; 
    
    public boolean afegir_equip(String nom, char tipus, int idcat, int idtemp) throws gestorEquipsException;
   
    public boolean afegir_membre(int jug, int cat, char tit) throws gestorEquipsException;
    
    public boolean afegir_temporada(Date anyini, Date anyfi) throws gestorEquipsException; 
    
    public usuari obtenir_usuari(String loggin) throws gestorEquipsException; 
    
    public Categoria obtenir_categoria(int id) throws gestorEquipsException;
    
    public Equip obtenir_equip(int id) throws gestorEquipsException; 
    
    public Membre obtenir_membre(int idjug,int ideqp) throws gestorEquipsException;
    
    public Jugador obtenir_jugador(int id) throws gestorEquipsException; 
    
    public Temporada obtenir_temporada(int id) throws gestorEquipsException;
    
    public boolean editar_usuari(usuari usu) throws gestorEquipsException;
    
    public boolean editar_categoria(Categoria cat) throws gestorEquipsException;
    
    public boolean editar_equip(Equip eq) throws gestorEquipsException;
    
    public boolean editar_jugador(Jugador jug) throws gestorEquipsException;
    
    public boolean editar_membre(Membre mem) throws gestorEquipsException;
    
    
    public boolean editar_temporada(Temporada temp) throws gestorEquipsException;
    
    public boolean eliminar_usuari(usuari usu) throws gestorEquipsException;
    
    public boolean eliminar_categoria(Categoria cat) throws gestorEquipsException;
    
    public boolean eliminar_equip(Equip eq) throws gestorEquipsException;
    
    public boolean eliminar_membre(Membre mem) throws gestorEquipsException;
    
    public boolean eliminar_jugador(Jugador jug) throws gestorEquipsException;
    
    public boolean eliminar_temporada(Temporada temp) throws gestorEquipsException;
    
    public boolean repetit(int dni) throws gestorEquipsException;
    
    public void conectionClose() throws gestorEquipsException;
    
    public HashMap carregar_jugador() throws gestorEquipsException;
    
   public HashMap<Integer,Categoria> carregar_categories() throws gestorEquipsException ;
    
    public ArrayList<Temporada> carregar_temporades() throws gestorEquipsException;
    
    public HashMap carregar_equips() throws gestorEquipsException;
    
    public HashMap<String, Membre> carregar_membres() throws gestorEquipsException;
    
    public void commit() throws gestorEquipsException ;

}  
