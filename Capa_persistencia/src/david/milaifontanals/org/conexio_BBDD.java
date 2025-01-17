/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package david.milaifontanals.org;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author isard
 */
public class conexio_BBDD implements Interficie_persistencia{
    
            Connection con;
            public HashMap<Integer,Categoria>hmcat;
            public HashMap<Integer,Equip>hmeqp=new HashMap<>();
            public HashMap<Integer,Jugador>hmjug=new HashMap<>();
            public HashMap<String,Membre>hmmem=new HashMap<>();
            public HashMap<Integer,Temporada>hmtemp=new HashMap<>();
            private PreparedStatement psAfegirUsuari;
            private PreparedStatement psAfegirCategoria;
            private PreparedStatement psAfegirEquip;
            private PreparedStatement psAfegirMembre;
            private PreparedStatement psAfegirTemporada;
            private PreparedStatement psAfegirJugador;
            private PreparedStatement psObtenirUsuari;
            private PreparedStatement psObtenirCategoria;
            private PreparedStatement psObtenirEquip;
            private PreparedStatement psObtenirMem;
            private PreparedStatement psObtenirJugador;
            private PreparedStatement psObtenirTemporada;
            private PreparedStatement psUpdateUsuari;
            private PreparedStatement psUpdateCategoria;
            private PreparedStatement psUpdateEquip;
            private PreparedStatement psUpdateMembre;
            private PreparedStatement psUpdateJugador;
            private PreparedStatement psUpdateTemporada;
            private PreparedStatement psEliminaUsuari;
            private PreparedStatement psEliminarCategoria;
            private PreparedStatement psEliminarEquip;
            private PreparedStatement psEliminarMembre;
            private PreparedStatement psRepetit;
            private PreparedStatement psEliminarJugador;
            private PreparedStatement psCarregarJugadors;
            private PreparedStatement psCarregarTemporades;
            private PreparedStatement psCarregarMembres;
            private PreparedStatement psObtenirMembrePerJugador;
             private PreparedStatement psObtenirUltimJugador;
            
        public conexio_BBDD() throws gestorEquipsException{
       
        String nomFitxer = "conexioBBDD.properties";
        Properties props = new Properties();
        try {
            FileInputStream pr = new FileInputStream(nomFitxer);
             props.load(pr);
             String[] claus={"url","usuari","contrasenya"};
             String[] valors = new String[3];
             for (int i = 0; i < claus.length; i++) {
                valors[i]=props.getProperty(claus[i]);
                
            }
             con = DriverManager.getConnection(valors[0],valors[1],valors[2]);
             con.setAutoCommit(false);
             pr.close();
        } catch (FileNotFoundException ex) {
            throw new gestorEquipsException("No s'ha pogut obrir el fitxer "+nomFitxer+"en el directori"+System.getProperty("user.dir"),ex);
        } catch (IOException ex) {
            throw new gestorEquipsException("No s'ha pogut carregar la informació del fitxer "+nomFitxer,ex);
        } catch (SQLException ex) {
            throw new gestorEquipsException("Error en conectar a la BBDD",ex);
        }
       
    }
    @Override
public String encriptar_contrasenya(String contrasenya) throws gestorEquipsException{
    String encriptat = null;
    try {
        
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        
        
        byte[] bytes = md.digest(contrasenya.getBytes());
        
       
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        
      
        encriptat = hexString.toString();
        
    } catch (NoSuchAlgorithmException ex) {
        try {
            throw new gestorEquipsException("Error al encriptar la contraseña", ex);
        } catch (gestorEquipsException ex1) {
            Logger.getLogger(conexio_BBDD.class.getName()).log(Level.SEVERE, null, ex1);//Aixo mho ha fet el netbeans, pero no estic segur perque 
        }
    }
  
                return encriptat;
}
      
   @Override
public boolean afegir_usuari(String nom, String password, String Loggin) throws gestorEquipsException {
    String encriptat;
    try {
        
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        
        
        byte[] bytes = md.digest(password.getBytes());
        
       
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        
      
        encriptat = hexString.toString();
        
    } catch (NoSuchAlgorithmException ex) {
        throw new gestorEquipsException("Error al encriptar la contraseña", ex);
    }
    
    
   
    if(psAfegirUsuari==null){
        try {
            try {
                psAfegirUsuari = con.prepareStatement("INSERT INTO usuari (login, password, nom) VALUES (?, ?, ?)");

            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per afegir usuaris",ex);
            }
            psAfegirUsuari.setString(1, Loggin);
            psAfegirUsuari.setString(2, encriptat);
            psAfegirUsuari.setString(3, nom);
           if( psAfegirUsuari.executeUpdate()!=1){
               return false;
           }
  
            
        } catch (SQLException ex) {
            throw new gestorEquipsException("Error al inserir usuari",ex);
            
        }
        
    }
    return true;
}

    @Override
    public boolean afegir_categoria(String nom, int edatMin, int edatMax) throws gestorEquipsException {
        if(psAfegirCategoria==null)
        {
            try {
                psAfegirCategoria= con.prepareStatement("INSERT INTO categoria (nom, edat_min, edat_max) VALUES (?, ?, ?)");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per afegir categories",ex);
            }
            try {
                psAfegirCategoria.setString(1, nom);
                psAfegirCategoria.setInt(2, edatMin);
                psAfegirCategoria.setInt(3, edatMax);
                if(psAfegirCategoria.executeUpdate()!=1){
                    return false;
                }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error al inserir categoria",ex);
            }
            
        }
        return true;
    }
            @Override
        public boolean afegir_jugador(String nom, String cognoms, char sexe,Date data_naix,String id_legal,String iban,Date any_revisio,String adreça,String poblacio,String codiPostal,String foto) throws gestorEquipsException{
                try {
                    String tp = Character.toString(sexe);
                    psAfegirJugador=con.prepareStatement("INSERT INTO jugador (nom,cognoms,sexe,data_naix,idlegal,iban,any_fi_revisió_médica,adreça,població,CodiPostal,foto) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
                    psAfegirJugador.setString(1, nom);
                    psAfegirJugador.setString(2, cognoms);
                    psAfegirJugador.setString(3, tp);
                    psAfegirJugador.setDate(4, data_naix);
                    psAfegirJugador.setString(5, id_legal);
                    psAfegirJugador.setString(6, iban);
                    psAfegirJugador.setDate(7, any_revisio);
                    psAfegirJugador.setString(8, adreça);
                    psAfegirJugador.setString(9, poblacio);
                    psAfegirJugador.setString(10, codiPostal);
                    psAfegirJugador.setString(11, foto);
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error al preparar statement per inserir jugador",ex);
                }
                try {
                    
                    if(psAfegirJugador.executeUpdate()!=1)
                        return false;
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error al executar query per inserir jugador",ex);
                }
            return true;
        }


    @Override
    public boolean afegir_equip(String nom, char tipus, int idcat, int idtemp) throws gestorEquipsException {
      
            try {
                psAfegirEquip=con.prepareStatement("INSERT INTO equip(nom,tipus,id_cat,temporada) VALUES (?,?,?,?)");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per afegir equips",ex);
            }
            try {
                String tp = Character.toString(tipus);
                psAfegirEquip.setString(1, nom);
               psAfegirEquip.setString(2, tp);
               psAfegirEquip.setInt(3, idcat);
               psAfegirEquip.setInt(4, idtemp);
               if(psAfegirEquip.executeUpdate()!=1){
                   return false;
               }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error al inserir equip",ex);
            }
        
        return true;
    }

    @Override
    public boolean afegir_membre(int jug, int eqp, char tit) throws gestorEquipsException {
        
            try {
                psAfegirMembre= con.prepareStatement("INSERT INTO membre(id_jugador,id_equip,titular) VALUES (?,?,?)");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per afegir membres",ex);
            }
            try {
                String tt= Character.toString(tit);
                psAfegirMembre.setInt(1, jug);
                psAfegirMembre.setInt(2, eqp);
                psAfegirMembre.setString(3,tt);
                if(psAfegirMembre.executeUpdate()!=1){
                    return false;
                }
            } catch (SQLException ex) {
                 throw new gestorEquipsException("Error al inserir membre",ex);
            }
        
        
        return true;
    }

    @Override
    public boolean afegir_temporada(Date anyini, Date anyfi) throws gestorEquipsException {
        if(psAfegirTemporada==null){
            try {
                psAfegirTemporada=con.prepareStatement("INSERT INTO temporada(anyini,anyfi) VALUES (?,?)");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per afegir temporades",ex);
            }
            try {
                psAfegirTemporada.setDate(1, anyini);
                psAfegirTemporada.setDate(2, anyfi);
                if(psAfegirTemporada.executeUpdate()!=1){
                    return false;
                }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error al inserir temporada",ex);
            }
        }
        
        return true;
    }

   @Override
public usuari obtenir_usuari(String loggin) throws gestorEquipsException {
    usuari usu = null;
    try {
        psObtenirUsuari = con.prepareStatement("SELECT * FROM usuari WHERE login = ?");
        psObtenirUsuari.setString(1, loggin);

        try (ResultSet rs = psObtenirUsuari.executeQuery()) {
            if (rs.next()) {
                String nom = rs.getString("nom");
                String login = rs.getString("login");
                String psswd = rs.getString("password");
                
                usu = new usuari(login, nom, psswd);
            }
        } catch (SQLException ex) {
            throw new gestorEquipsException("Error al ejecutar la consulta para cargar el usuario", ex);
        }
    } catch (SQLException ex) {
        throw new gestorEquipsException("Error al preparar el statement para recuperar el usuario", ex);
    } 
    
    return usu;
}


    @Override
    public Categoria obtenir_categoria(int id) throws gestorEquipsException {
        Categoria cat = hmcat.get(id);
        if (cat != null) {
            return cat;
        }
       
       
            try {
                psObtenirCategoria=con.prepareStatement("select * from categoria where id = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per recuperar categories",ex);
            }
            try {
                psObtenirCategoria.setInt(1, id);
                 ResultSet rs = psObtenirCategoria.executeQuery();
                 if(rs.next()){
                int idc=rs.getInt("id");
                String nom = rs.getString("nom");
                int anyini=rs.getInt("edat_min");
                int anyfi = rs.getInt("edat_max");
                cat = new Categoria(idc,nom,anyfi,anyini);
            }
                 
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per carregar categoria",ex);
            }finally{
                try {
    if (psObtenirCategoria != null) {
        psObtenirCategoria.close();
    }
} catch (SQLException e) {
    System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
}

            }
           
            
        
        hmcat.put(id, cat);
        return cat;
    }
    

    @Override
    public Equip obtenir_equip(int id) throws gestorEquipsException { 
        Equip eqp= hmeqp.get(id);
        if(eqp!=null){
            return eqp;
        }
       
            try {
                psObtenirEquip=con.prepareStatement("select * from equip where id = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per recuperar equips",ex);
            }
            try {
                psObtenirEquip.setInt(1, id);
                 ResultSet rs = psObtenirEquip.executeQuery();
                 if(rs.next()){
                int ide=rs.getInt("id");
                String nom = rs.getString("nom");
                String tipus=rs.getString("tipus");
                int idcat = rs.getInt("id_cat");
                int idtemp= rs.getInt("tempporada");
                Categoria cat=hmcat.get(idcat);
                Temporada temp = hmtemp.get(idtemp);
                if(cat==null){
                    cat=obtenir_categoria(idcat);
            }
                if(temp==null){
                    temp=obtenir_temporada(idtemp);
                }
                char tip= tipus.charAt(0);
                eqp=new Equip(ide,nom,tip,cat,temp);
                 }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per carregar categoria",ex);
            }
           
            
        
        return eqp;
    }
   
    @Override
    public Membre obtenir_membre(int idjug,int ideqp) throws gestorEquipsException {
        String key= ""+Integer.toString(ideqp)+Integer.toString(ideqp);
        Membre mem= hmmem.get(key);
        if(mem!=null){
            return mem;
        }
       
            try {
                psObtenirMem=con.prepareStatement("select * from membre where id_jugador = ? and id_equip =?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per recuperar equips",ex);
            }
            try {
                psObtenirMem.setInt(1, idjug);
                psObtenirMem.setInt(2, ideqp);
                 ResultSet rs = psObtenirMem.executeQuery();
                 if(rs.next()){
                     int idjuga=rs.getInt("id_jugador");
                     int idequip=rs.getInt("id_equip");
                     String titu = rs.getString("titular");
                     
                
                Jugador jug=hmjug.get(idjuga);
                if(jug==null){
                    jug=obtenir_jugador(idjuga);
                }
                Equip eq = hmeqp.get(idequip);
                if(eq==null){
                    eq=obtenir_equip(idequip);
                }
                char tit= titu.charAt(0);
                mem=new Membre(jug,eq,tit);
                 }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per carregar membre",ex);
            }
           
            
        
        return mem;
    }

     @Override
    public Jugador obtenir_jugador(int id) throws gestorEquipsException{
    Jugador jug= hmjug.get(id);
        if(jug!=null){
            return jug;
        }
        
            try {
                psObtenirJugador=con.prepareStatement("select * from jugador where id = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per recuperar jugadors",ex);
            }
            try {
                psObtenirEquip.setInt(1, id);
                 ResultSet rs = psObtenirJugador.executeQuery();
                 if(rs.next()){
                int idj=rs.getInt("id");
                String nom=rs.getString("nom");
                String cognom = rs.getString("cognoms");
                String sexe=rs.getString("Sexe");
                Date dataNaix=rs.getDate("data_naix");
                String dni=rs.getString("idLegal");
                String iban = rs.getString("IBAN");
                Date anyRev=rs.getDate("ANY_FI_REVISIÓ_MÉDICA");
                String Adreça=rs.getString("adreça");
                String poblacio=rs.getString("població");
                String cp=rs.getString("CodiPostal");
                String url = rs.getString("foto");
                
                char sex= sexe.charAt(0);
                jug=new Jugador(idj,nom,cognom,sex,dataNaix,dni,iban,anyRev,Adreça,url,poblacio,cp);
                 }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per carregar categoria",ex);
            }
           
            
        
        return jug;
    }
   
    @Override
    public Temporada obtenir_temporada(int id) throws gestorEquipsException {
        Temporada temp= hmtemp.get(id);
       
            try {
                psObtenirTemporada=con.prepareStatement("select * from temporada where id = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per recuperar temporades",ex);
            }
            try {
                psObtenirTemporada.setInt(1, id);
                 ResultSet rs = psObtenirTemporada.executeQuery();
                 if(rs.next()){
                int idt=rs.getInt("id");
                Date anyini= rs.getDate("anyini");
                Date anyfi=rs.getDate("anyfi");
                
                temp=new Temporada(idt,anyini,anyfi);
                 }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per carregar categoria",ex);
            }
        return temp;   
    }

    @Override
    public boolean editar_usuari(usuari usu) throws gestorEquipsException {
            if(psUpdateUsuari==null){
                try {
                    psUpdateUsuari=con.prepareStatement("Update usuari set nom = ?, password =? where login =?");
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en preparar el statement per canviar usuaris",ex);                }
            }
                try {
                    psUpdateUsuari.setString(1, usu.nomUsuari);
                    String encriptat;
                    try {

                        MessageDigest md = MessageDigest.getInstance("SHA-512");


                        byte[] bytes = md.digest(usu.contrasenya.getBytes());


                        StringBuilder hexString = new StringBuilder();
                        for (byte b : bytes) {

                            String hex = Integer.toHexString(0xff & b);
                            if (hex.length() == 1) hexString.append('0');
                            hexString.append(hex);
                        }


                        encriptat = hexString.toString();

                    } catch (NoSuchAlgorithmException ex) {
                        throw new gestorEquipsException("Error al encriptar la contraseña", ex);
                    }
                    psUpdateUsuari.setString(2, encriptat);
                    psUpdateUsuari.setString(3, usu.login);
                    psUpdateUsuari.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en executar la query per actualitzar usuaris",ex);
                }
    }

    @Override
    public boolean editar_categoria(Categoria cat) throws gestorEquipsException {

        if(psUpdateCategoria==null){
            try {
                psUpdateCategoria=con.prepareStatement("Update categoria set nom =?, edat_min=?,edat_max=? where id =?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per canviar categories",ex); 
            }}
            try {
                psUpdateCategoria.setString(1, cat.getNom());
                psUpdateCategoria.setInt(2, cat.getEdatMin());
                psUpdateCategoria.setInt(3, cat.getEdatMax());
                psUpdateCategoria.setInt(3, cat.getId());
                psUpdateCategoria.executeUpdate();
                return true;
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per actualitzar categories",ex);
            }
        
    }

    @Override
    public boolean editar_equip(Equip eq) throws gestorEquipsException {
         if(psUpdateEquip==null){
            try {
                psUpdateEquip=con.prepareStatement("Update equip set nom =?, tipus=?,id_cat=? where id =?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per canviar equips",ex); 
            }}
            try {
                psUpdateEquip.setString(1, eq.getNomEquip());
                psUpdateEquip.setString(2, String.valueOf(eq.getTipus()).trim().substring(0, 1));
                psUpdateEquip.setInt(3, eq.getCat().getId());
                psUpdateEquip.setInt(4, eq.getIdEq());
                psUpdateEquip.executeUpdate();
                return true;
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per actualitzar equip",ex);
            }
        
    }
    

    @Override
    public boolean editar_membre(Membre mem) throws gestorEquipsException {
          
            try {
                psUpdateMembre=con.prepareStatement("Update membre set titular=? where id_jugador =? and id_equip=?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per canviar equips",ex); 
            }
            try {
                psUpdateMembre.setString(1, String.valueOf(mem.getTitular()));
                psUpdateMembre.setInt(2, mem.getJug().getId());
                psUpdateMembre.setInt(3, mem.getEq().getIdEq());
                
                psUpdateMembre.executeUpdate();
                return true;
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per actualitzar membres",ex);
            }
        
    }

    @Override
    public boolean editar_jugador(Jugador jug) throws gestorEquipsException{
        if(psUpdateJugador==null){
            try {
                psUpdateJugador=con.prepareStatement("Update jugador set nom=?,cognoms=?,sexe=?,data_naix=?,idlegal=?,iban=?,any_fi_revisió_medica=?, adreça=?,foto=?,població=?,CodiPostal=? where id=?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per canviar jugadors",ex); 
            }}
            try {
                psUpdateJugador.setString(1, jug.getNomJugador());
                psUpdateJugador.setString(2, jug.getCognoms());
                psUpdateJugador.setString(3, String.valueOf(jug.getSexe()));
                psUpdateJugador.setDate(4, (Date) jug.getDataNaix());
                psUpdateJugador.setString(5, jug.getIdLegal());
                psUpdateJugador.setString(6, jug.getIban());
                psUpdateJugador.setDate(7, (Date) jug.getAnyFiRevisioMedica());
                psUpdateJugador.setString(8, jug.foto);
                psUpdateJugador.setInt(9, jug.getId());
                psUpdateJugador.executeUpdate();
                return true;
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per actualitzar jugadors",ex);
            }
        
    }
    
    @Override
    public boolean editar_temporada(Temporada temp) throws gestorEquipsException {
        if(psUpdateTemporada==null){
            try {
                psUpdateTemporada=con.prepareStatement("Update temporada set anyini=?,anyfi=? where id =?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per canviar temporades",ex); 
            }}
            try {
                psUpdateTemporada.setDate(1, (Date) temp.getAnyIni());
                psUpdateTemporada.setDate(2, (Date) temp.getAnyFi());
                psUpdateTemporada.setInt(3, temp.idTemp);
                psUpdateTemporada.executeUpdate();
                return true;
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per actualitzar temporades",ex);
            }
        
    }

    @Override
    public boolean eliminar_usuari(usuari usu) throws gestorEquipsException {
        if(psEliminaUsuari==null){
            try {
                psEliminaUsuari=con.prepareStatement("delete from usuari where login = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per eliminar usuaris",ex);
            }
            
        }
                try {
                    psEliminaUsuari.setString(1, usu.login);
                    psEliminaUsuari.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en preparar el statement per eliminar usuaris",ex);
                }
    }

    @Override
    public boolean eliminar_categoria(Categoria cat) throws gestorEquipsException {
        if(psEliminarCategoria==null){
            try {
                psEliminarCategoria=con.prepareStatement("delete from categoria where id = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per eliminar categories",ex);
            }
            
        }
                try {
                    psEliminarCategoria.setInt(1, cat.getId());
                    psEliminarCategoria.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en preparar el statement per eliminar categories",ex);
                }
    }

    @Override
    public boolean eliminar_equip(Equip eq) throws gestorEquipsException {
             
            try {
                psEliminarEquip=con.prepareStatement("delete from equip where id = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per eliminar equips",ex);
            }
            
        
                try {
                    psEliminarEquip.setInt(1, eq.getIdEq());
                    psEliminarEquip.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en preparar el statement per eliminar equips",ex);
                }  
    }

    @Override 
    public boolean eliminar_membre(Membre mem) throws gestorEquipsException {
      
            try {
                psEliminarMembre=con.prepareStatement("delete from membre where id_equip = ? and id_jugador = ?");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per eliminar membres",ex);
            }
            
        
                try {
                    psEliminarMembre.setInt(1, mem.getEq().idEq);
                    psEliminarMembre.setInt(2,mem.getJug().getId());
                    psEliminarMembre.executeUpdate();
                    return true;
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en preparar el statement per eliminar membres",ex);
                }    
    }   


    @Override
public boolean eliminar_temporada(Temporada temp) throws gestorEquipsException {
    PreparedStatement psEliminarTemp = null;

    try {
        psEliminarTemp = con.prepareStatement("DELETE FROM temporada WHERE id = ?");
        psEliminarTemp.setInt(1, temp.getIdTemp());
        int rowsAffected = psEliminarTemp.executeUpdate();

        if (rowsAffected == 0) {
            throw new gestorEquipsException("No se encontró la temporada a eliminar.");
        }

        return true;

    } catch (SQLException ex) {
        
        if (ex.getErrorCode() == 1451) { 
            throw new gestorEquipsException("No se puede eliminar la temporada porque tiene equipos asociados.");
        }
        throw new gestorEquipsException("Error al eliminar la temporada: " + ex.getMessage(), ex);
    } finally {
        try {
            if (psEliminarTemp != null) psEliminarTemp.close();
        } catch (SQLException ex) {
            throw new gestorEquipsException("Error al cerrar recursos de la base de datos", ex);
        }
    }
}

  
    @Override
    public boolean repetit(int dni) throws gestorEquipsException {
        if(psRepetit==null){
            try {
                psRepetit=con.prepareStatement("Select id from jugador where idlegal=?");
                psRepetit.setInt(dni, dni);
                ResultSet rs=psRepetit.executeQuery();
                if(rs.next()){
                    return true;
                }
                
            } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en buscar repetits",ex);
            }
            
        }
                return false;
    }


    @Override
    public void conectionClose() throws gestorEquipsException {
        if (con != null) {
            try {
                con.close();
                
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error al tancar la contexió a la base de dades", ex);
            }
        }
    }

@Override
public boolean eliminar_jugador(Jugador jug) throws gestorEquipsException {
    String sql = "DELETE FROM jugador WHERE id = ?";
    
    try (PreparedStatement psEliminarJugador = con.prepareStatement(sql)) {
        psEliminarJugador.setInt(1, jug.getId());
        int filasAfectadas = psEliminarJugador.executeUpdate();
        
        if (filasAfectadas == 0) {
            throw new gestorEquipsException("No se pudo eliminar el jugador con ID: " + jug.getId() + ". Es posible que no exista en la base de datos.");
        }
        
        System.out.println("Jugador con ID " + jug.getId() + " eliminado correctamente.");
        return true;
    } catch (SQLIntegrityConstraintViolationException ex) {
        throw new gestorEquipsException("No se puede eliminar el jugador con ID: " + jug.getId() + " debido a restricciones de clave foránea.", ex);
    } catch (SQLException ex) {
        throw new gestorEquipsException("Error SQL al intentar eliminar el jugador con ID: " + jug.getId() + ". SQLState: " + ex.getSQLState() + ", ErrorCode: " + ex.getErrorCode(), ex);
    }
}



  @Override
public HashMap<Integer,Categoria> carregar_categories() throws gestorEquipsException {
    this.hmcat=new HashMap<>();

    try {
        psObtenirCategoria = con.prepareStatement("SELECT * FROM categoria");
        ResultSet rs = psObtenirCategoria.executeQuery();
        while (rs.next()) { 
            int idc = rs.getInt("id");
            String nom = rs.getString("nom");
            int anyini = rs.getInt("edat_min");
            int anyfi = rs.getInt("edat_max");
            Categoria cat = new Categoria(idc, nom, anyfi, anyini);
            hmcat.put(idc,cat);
        }
        if (hmcat.size() != 6) {
            throw new gestorEquipsException("No se han encontrado las 6 categorías esperadas en la base de datos.");
        }
    } catch (SQLException ex) {
        throw new gestorEquipsException("Error al cargar categorías desde la base de datos.", ex);
    } finally {
  
        try {
            if (psObtenirCategoria != null) {
                psObtenirCategoria.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar el PreparedStatement: " + ex.getMessage());
        }
    }
   return hmcat;
}

            @Override
  public HashMap carregar_jugador() throws gestorEquipsException{
      hmjug.clear();
        
       
            try {
                psCarregarJugadors=con.prepareStatement("select * from jugador ");
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en preparar el statement per recuperar jugadors",ex);
            }
            try {
                 ResultSet rs = psCarregarJugadors.executeQuery();
                 while(rs.next()){
                int idj=rs.getInt("id");
                String nom=rs.getString("nom");
                String cognom = rs.getString("cognoms");
                String sexe=rs.getString("Sexe");
                Date dataNaix=rs.getDate("data_naix");
                String dni=rs.getString("idLegal");
                String iban = rs.getString("IBAN");
                Date anyRev=rs.getDate("ANY_FI_REVISIÓ_MÉDICA");
                String Adreça=rs.getString("adreça");
                String url = rs.getString("foto");
                
                char sex= sexe.charAt(0);
               Jugador jug=new Jugador(idj,nom,cognom,sex,dataNaix,dni,iban,anyRev,Adreça,url);
               hmjug.put(idj, jug);
          
                 }
            } catch (SQLException ex) {
                throw new gestorEquipsException("Error en executar la query per carregar jugador",ex);
            }
           
            
        
        return hmjug;
    }
            @Override
    public ArrayList<Temporada> carregar_temporades() throws gestorEquipsException {
    ArrayList<Temporada> listaTemporadas = new ArrayList<>();
    ResultSet rs = null;
    
    
        try {
            psCarregarTemporades = con.prepareStatement("SELECT * FROM temporada");
        } catch (SQLException ex) {
            throw new gestorEquipsException("Error al crear el prepared statement", ex);
        }
    
    try {
        rs = psCarregarTemporades.executeQuery();
        while (rs.next()) {
            int idt = rs.getInt("id");
            Date anyini = rs.getDate("anyini");
            Date anyfi = rs.getDate("anyfi");
            Temporada temp = new Temporada(idt, anyini, anyfi);
            listaTemporadas.add(temp);
        }
    } catch (SQLException ex) {
        throw new gestorEquipsException("Error al cargar las temporadas", ex);
    }
    
    return listaTemporadas;
}
    @Override
public HashMap<Integer, Equip> carregar_equips() throws gestorEquipsException {
    hmeqp.clear(); 
    Equip eqp = null;
    
   
        try {
            psObtenirEquip = con.prepareStatement("SELECT * FROM equip");
        } catch (SQLException ex) {
            throw new gestorEquipsException("Error en preparar el statement para recuperar equipos", ex);
        }
    
    try {
        ResultSet rs = psObtenirEquip.executeQuery();
        while (rs.next()) {
            int ide = rs.getInt("id");
            String nom = rs.getString("nom");
            String tipus = rs.getString("tipus");
            int idcat = rs.getInt("id_cat");
            int idtemp = rs.getInt("temporada");
            Categoria cat = hmcat.get(idcat);
            if (cat == null) {
                cat = obtenir_categoria(idcat);
            }
            Temporada temp = obtenir_temporada(idtemp);            
            char tip = tipus.charAt(0);
            eqp = new Equip(ide, nom, tip, cat, temp);
            hmeqp.put(eqp.getIdEq(), eqp);
        }
    } catch (SQLException ex) {
        throw new gestorEquipsException("Error al ejecutar la query para cargar equipos", ex);
    } finally {
        try {
            if (psObtenirEquip != null) {
                psObtenirEquip.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar el PreparedStatement: " + ex.getMessage());
        }
    }

    return hmeqp;
}
            @Override
            public HashMap<String, Membre> carregar_membres() throws gestorEquipsException {
    hmmem.clear(); 
    Membre mem = null;

    try {
        psCarregarMembres = con.prepareStatement("SELECT * FROM Membre");
        ResultSet rs = psCarregarMembres.executeQuery();
        while (rs.next()) {
            int idjuga = rs.getInt("id_jugador");
            int idequip = rs.getInt("id_equip");
            String titu = rs.getString("titular");
            Jugador jug = hmjug.get(idjuga);
            if (jug == null) {
                jug = obtenir_jugador(idjuga);
            }
            Equip eq = hmeqp.get(idequip);
            if (eq == null) {
                eq = obtenir_equip(idequip);
            }
            char tit = titu.charAt(0);
            mem = new Membre(jug, eq, tit);
            String clau = ""+jug.idLegal+eq.idEq;
            hmmem.put(clau, mem);
           
        }
    } catch (SQLException ex) {
        throw new gestorEquipsException("Error al cargar membres", ex);
    }
    return hmmem;
}
             @Override
    public ArrayList <Membre> obtenir_membre_per_jugador(int idjuga)throws gestorEquipsException{
        ArrayList <Membre> mem = new ArrayList();
                try {
                    psObtenirMembrePerJugador=con.prepareStatement("SELECT * FROM MEMBRE WHERE ID_JUGADOR = ?");
                     psObtenirMembrePerJugador.setInt(1, idjuga);
                    ResultSet rs=psObtenirMembrePerJugador.executeQuery();
                    while(rs.next()){
                        int idEquip=rs.getInt("id_equip");
                        Membre membre=obtenir_membre(idjuga,idEquip);
                        mem.add(membre);
                    }
                    psObtenirMembrePerJugador.close();
                } catch (Exception ex) {
                    throw new gestorEquipsException("Error: ", ex);
                } 
        return mem;
    }

            @Override
    public int obtenir_ultim_jugador()throws gestorEquipsException{
        ResultSet rs;
        int idjug=-1;
                try {
                    psObtenirUltimJugador=con.prepareStatement("SELECT id FROM JUGADOR",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                } catch (SQLException ex) {
                     throw new gestorEquipsException("Error: ", ex);
                }
                try {
                    rs=psObtenirUltimJugador.executeQuery();
                    if(rs.last()){
                        idjug=rs.getInt("id");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(conexio_BBDD.class.getName()).log(Level.SEVERE, null, ex);
                }
                return idjug;
    }
    @Override
    public void commit() throws gestorEquipsException {
                try {
                  con.commit(); 
                } catch (SQLException ex) {
                    throw new gestorEquipsException("Error en realitzar el commit", ex);
                
                }
    }
      

}

